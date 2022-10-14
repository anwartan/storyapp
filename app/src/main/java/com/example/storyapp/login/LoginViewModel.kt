package com.example.storyapp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.storyapp.data.AuthRepository
import com.example.storyapp.model.UserModel
import com.example.storyapp.model.UserPreference


class LoginViewModel(private val authRepository: AuthRepository,private val pref: UserPreference) : ViewModel() {
    fun login(email: String, password: String)  =  authRepository.login(email,password)

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }


}