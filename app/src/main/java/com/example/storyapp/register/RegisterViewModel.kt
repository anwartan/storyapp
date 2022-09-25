package com.example.storyapp.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.event.Event
import com.example.storyapp.source.ApiConfig
import com.example.storyapp.source.BaseResponse
import com.example.storyapp.utils.mapper.ResponseMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel :ViewModel() {

    private val _isSignUp = MutableLiveData<Boolean>()
    val isSignUp:LiveData<Boolean> = _isSignUp
    private val _isLoading = MutableLiveData(false)
    val isLoading:LiveData<Boolean> = _isLoading
    private val _message = MutableLiveData<Event<String>>()
    val message:LiveData<Event<String>> = _message

    fun register(email:String,name:String,password:String) {
        val client = ApiConfig.getApiService().register(email,name,password)
        _isLoading.value=true
        client.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val responseBody = response.body()
                _isLoading.value=false
                if(response.isSuccessful && responseBody!=null){
                    if(!responseBody.error!!){
                        _isSignUp.value=true
                    }
                }else{
                    val message = response.errorBody()
                        ?.let { ResponseMapper.mapErrorResponseToBaseResponse(it).message }?:"ERROR"
                    _message.value= Event(message)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                _isLoading.value=false
                _message.value = Event(t.message?:"ERROR")
            }

        })
    }



}