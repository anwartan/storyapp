package com.example.storyapp.source.remote

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("loginResult")
    val loginResult : UserInfoResponse
    ): BaseResponse()