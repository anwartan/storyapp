package com.example.storyapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.event.Event
import com.example.storyapp.model.StoryModel
import com.example.storyapp.source.ApiConfig
import com.example.storyapp.source.StoriesResponse
import com.example.storyapp.utils.mapper.ResponseMapper
import com.example.storyapp.utils.mapper.StoryMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {

    private val _stories = MutableLiveData<List<StoryModel>>()
    val stories :LiveData<List<StoryModel>> = _stories
    private val _isLoading = MutableLiveData(false)
    val isLoading:LiveData<Boolean> = _isLoading
    private val _message = MutableLiveData<Event<String>>()
    val message:LiveData<Event<String>> = _message
    fun getStories(){

        val client = ApiConfig.getApiService().getStories(null,null,null)
        _isLoading.value=true
        client.enqueue(object : Callback<StoriesResponse>{
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                _isLoading.value=false
                val responseBody = response.body()
                if(response.isSuccessful && responseBody!=null){
                    _stories.value = StoryMapper.mapResponsesToDomains(responseBody.listStory)
                }else{
                    val message = response.errorBody()
                        ?.let { ResponseMapper.mapErrorResponseToBaseResponse(it).message }?:"ERROR"
                    _message.value= Event(message)
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                _isLoading.value=false
                _message.value=Event(t.message ?: "ERROR")
            }

        })
    }

}