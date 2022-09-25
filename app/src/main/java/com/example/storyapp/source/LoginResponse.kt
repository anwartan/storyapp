package com.example.storyapp.source

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("loginResult")
    val loginResult : UserInfoResponse
    ):BaseResponse()