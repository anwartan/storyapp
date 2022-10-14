package com.example.storyapp.addStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.data.Result
import com.example.storyapp.data.StoryRepository
import java.io.File

class AddStoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun addStory(description: String, lat: Float?, lon: Float?, image: File):LiveData<Result<Boolean>> = storyRepository.addStory(description, lat, lon, image)
}