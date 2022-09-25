package com.example.storyapp.source

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email:String, @Field("password") password:String): Call<LoginResponse>
    @FormUrlEncoded
    @POST("register")
    fun register(@Field("email") email:String, @Field("name") name:String, @Field("password") password:String) : Call<BaseResponse>

    @Multipart
    @POST("stories")
    fun addStory(@Part("description")
                  description: RequestBody,
                 @Part
                  photo: MultipartBody.Part,
                 @Part("lat")
                  lat: RequestBody,
                 @Part("lon")
                  lon: RequestBody
    ) : Call<BaseResponse>

    @GET("stories")
    fun getStories(@Query("page") page: Int?, @Query("size") size:Int?, @Query("location") location:String?): Call<StoriesResponse>


}