package com.example.storyapp.source

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name : String,
    @SerializedName("token")
    val token : String,
)