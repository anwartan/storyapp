package com.example.storyapp.source.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(@Field("email") email:String, @Field("password") password:String): LoginResponse
    @FormUrlEncoded
    @POST("register")
    suspend fun register(@Field("email") email:String, @Field("name") name:String, @Field("password") password:String) : BaseResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(@Part("description")
                  description: RequestBody,
                 @Part
                  photo: MultipartBody.Part,
                 @Part("lat")
                  lat: RequestBody?,
                 @Part("lon")
                  lon: RequestBody?
    ) : BaseResponse

    @GET("stories")
    suspend fun getStories(@Query("page") page: Int?, @Query("size") size:Int?, @Query("location") location:String?): StoriesResponse

    @GET("stories")
    suspend fun findStories(@Query("page") page: Int?, @Query("size") size:Int?, @Query("location") location:String?): StoriesResponse

}