package com.example.storyapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.storyapp.model.UserModel
import com.example.storyapp.model.UserPreference
import com.example.storyapp.source.remote.ApiService
import com.example.storyapp.utils.mapper.UserMapper

class AuthRepository(private val apiService: ApiService,private val userPreference: UserPreference) {

    fun login(email: String, password: String):LiveData<Result<UserModel>>  = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            if(response.error == true){
                emit(Result.Error(response.message?:"SERVER ERROR"))
            }
            val responseBody = response.loginResult
            val userModel = UserMapper.mapResponseToDomain(responseBody)
            userPreference.login(userModel)
            emit(Result.Success(userModel))
        }catch (e:Exception){
            emit(Result.Error(e.message.toString()))
        }

    }

    fun register(email:String,name:String,password:String):LiveData<Result<Boolean>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(email,name, password)

            if(response.error == true){
                emit(Result.Error(response.message?:"SERVER ERROR"))
            }
            emit(Result.Success(true))
        }catch (e:Exception){
            emit(Result.Error(e.message.toString()))
        }
    }
}