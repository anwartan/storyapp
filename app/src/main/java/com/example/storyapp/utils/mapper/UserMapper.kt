package com.example.storyapp.utils.mapper

import com.example.storyapp.model.UserModel
import com.example.storyapp.source.UserInfoResponse

object UserMapper {

    fun mapResponseToDomain(input:UserInfoResponse):UserModel{
        return UserModel(
            userId = input.userId,
            name = input.name,
            token = input.token
        )
    }
}