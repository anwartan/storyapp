package com.example.storyapp.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.model.StoryModel

class HomeViewModel(private val storyRepository: StoryRepository):ViewModel() {

    var stories = MutableLiveData<PagingData<StoryModel>>()

    fun getStories(){

        stories = storyRepository.getStories().cachedIn(viewModelScope) as MutableLiveData<PagingData<StoryModel>>
    }
}