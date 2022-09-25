package com.example.storyapp.addStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.event.Event
import com.example.storyapp.source.ApiConfig
import com.example.storyapp.source.BaseResponse
import com.example.storyapp.utils.mapper.ResponseMapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddStoryViewModel : ViewModel() {
    private val _added = MutableLiveData<Boolean>()
    val added: LiveData<Boolean> = _added
    private val _isLoading = MutableLiveData(false)
    val isLoading:LiveData<Boolean> = _isLoading
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message
    fun addStory(description: String, lat: Float?, lon: Float?, image: File) {
        _isLoading.value=true
        val requestImageFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multiPart = MultipartBody.Part.createFormData("photo", image.name, requestImageFile)
        val descriptionRequest = description.toRequestBody("text/plain".toMediaType())
        val latRequest = lat?.toString()?.toRequestBody("text/plain".toMediaType())
        val lonRequest = lon?.toString()?.toRequestBody("text/plain".toMediaType())

        val client = ApiConfig.getApiService()
            .addStory(descriptionRequest, multiPart, latRequest, lonRequest)

        client.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val responseBody = response.body()
                _isLoading.value=false
                if (response.isSuccessful && responseBody != null) {
                    _added.value = responseBody.error != true
                }else{
                    val message = response.errorBody()
                        ?.let { ResponseMapper.mapErrorResponseToBaseResponse(it).message }?:"ERROR"
                    _message.value= Event(message)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                _message.value = Event(t.message ?: "ERROR")
                _isLoading.value=false
            }

        })
    }
}