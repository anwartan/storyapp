package com.example.storyapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.model.StoryModel
import com.example.storyapp.source.remote.ApiService
import com.example.storyapp.source.remote.StoryResponse
import com.example.storyapp.utils.mapper.StoryMapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class StoryRepository(private val apiService: ApiService) {
    fun getStories():LiveData<PagingData<StoryModel>>{
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

    fun addStory(description: String, lat: Float?, lon: Float?, image: File) : LiveData<Result<Boolean>> = liveData {
        emit(Result.Loading)
        try {
            val requestImageFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val multiPart = MultipartBody.Part.createFormData("photo", image.name, requestImageFile)
            val descriptionRequest = description.toRequestBody("text/plain".toMediaType())
            val latRequest = lat?.toString()?.toRequestBody("text/plain".toMediaType())
            val lonRequest = lon?.toString()?.toRequestBody("text/plain".toMediaType())
            val response =
                apiService.addStory(descriptionRequest, multiPart, latRequest, lonRequest)
            val responseBody = response.error
            if (responseBody == true) {
                emit(Result.Error(response.message ?: "SERVER ERROR"))
            }
            emit(Result.Success(true))

        }catch (e:Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDataStories():LiveData<Result<List<StoryModel>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStories(null,null,"1")
            if(response.error==true){
                emit(Result.Error(response.message?:"SERVER ERROR"))
            }
            val responseBody = StoryMapper.mapResponsesToDomains(response.listStory)

            emit(Result.Success(responseBody))

        }catch (e:Exception){
            emit(Result.Error(e.message.toString()))
        }
    }
}