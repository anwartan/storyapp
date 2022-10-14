package com.example.storyapp.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.data.Result
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.model.StoryModel

class MapViewModel(private val storyRepository: StoryRepository) : ViewModel() {


    fun getStories():LiveData<Result<List<StoryModel>>> = storyRepository.getDataStories()

}