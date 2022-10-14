package com.example.storyapp.register

import androidx.lifecycle.ViewModel
import com.example.storyapp.data.AuthRepository

class RegisterViewModel(private val authRepository: AuthRepository) :ViewModel() {

    fun register(email: String,name:String, password: String) =authRepository.register(email,name,password)

}