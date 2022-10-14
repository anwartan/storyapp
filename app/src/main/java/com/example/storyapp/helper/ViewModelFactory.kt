package com.example.storyapp.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.Injection
import com.example.storyapp.addStory.AddStoryViewModel
import com.example.storyapp.home.HomeViewModel
import com.example.storyapp.login.LoginViewModel
import com.example.storyapp.main.MainViewModel
import com.example.storyapp.map.MapViewModel
import com.example.storyapp.register.RegisterViewModel


class ViewModelFactory(private val context:Context):ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val storyRepository = Injection.provideRepository(context)
        val authRepository = Injection.provideAuthRepository(context)
        val pref = Injection.provideUserPreference(context)
        return when {

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {

                LoginViewModel(authRepository,pref) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {

                HomeViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(AddStoryViewModel::class.java)->{
                AddStoryViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(MapViewModel::class.java)->{
                MapViewModel(storyRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}