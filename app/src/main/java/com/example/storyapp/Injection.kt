package com.example.storyapp

import android.content.Context
import com.example.storyapp.data.AuthRepository
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.model.UserPreference
import com.example.storyapp.source.remote.ApiService
import com.example.storyapp.source.remote.AuthInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injection {

    private fun provideApiService(context: Context): ApiService {
        val userPref = provideUserPreference(context)
        val loggingInterceptor = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val authInterceptor = AuthInterceptor(userPref)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun provideUserPreference(context: Context):UserPreference{
        return UserPreference.getInstance(context.dataStore)
    }

    fun provideRepository(context:Context):StoryRepository{

        val apiService = provideApiService(context)
        return StoryRepository(apiService)
    }
    fun provideAuthRepository(context: Context):AuthRepository{
        val apiService = provideApiService(context)
        return AuthRepository(apiService,provideUserPreference(context))
    }

}