package com.example.storyapp.source

import com.google.gson.annotations.SerializedName

data class StoriesResponse(
    @SerializedName("listStory")
    val listStory : List<StoryResponse>
):BaseResponse()