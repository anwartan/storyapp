package com.example.storyapp.source.remote

import com.example.storyapp.model.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(private val pref:UserPreference):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        runBlocking {
            requestBuilder.addHeader("Authorization","Bearer ${pref.getUser().first().token}")
        }
        return chain.proceed(requestBuilder.build())
    }


}