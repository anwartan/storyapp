package com.example.storyapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.model.StoryModel

class HomeViewModel(storyRepository: StoryRepository):ViewModel() {

    val stories : LiveData<PagingData<StoryModel>> = storyRepository.getStories().cachedIn(viewModelScope)

}