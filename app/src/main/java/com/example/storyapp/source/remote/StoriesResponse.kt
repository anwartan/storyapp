package com.example.storyapp.source.remote

import com.google.gson.annotations.SerializedName

data class StoriesResponse(
    @SerializedName("listStory")
    val listStory : List<StoryResponse>
): BaseResponse()