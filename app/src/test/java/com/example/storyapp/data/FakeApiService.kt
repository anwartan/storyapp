package com.example.storyapp.data

import com.example.storyapp.DataDummy
import com.example.storyapp.source.remote.ApiService
import com.example.storyapp.source.remote.BaseResponse
import com.example.storyapp.source.remote.LoginResponse
import com.example.storyapp.source.remote.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApiService:ApiService {
    override suspend fun login(email: String, password: String): LoginResponse {
        return DataDummy.generateDummyLoginResponse()
    }

    override suspend fun register(email: String, name: String, password: String): BaseResponse {
        return DataDummy.generateDummyBaseResponse()
    }

    override suspend fun addStory(
        description: RequestBody,
        photo: MultipartBody.Part,
        lat: RequestBody?,
        lon: RequestBody?
    ): BaseResponse {
        return DataDummy.generateDummyBaseResponse()
    }

    override suspend fun getStories(page: Int?, size: Int?, location: String?): StoriesResponse {
        return DataDummy.generateDummyStoriesResponse()
    }

    override suspend fun findStories(page: Int?, size: Int?, location: String?): StoriesResponse {
        return DataDummy.generateDummyStoriesResponse()
    }
}