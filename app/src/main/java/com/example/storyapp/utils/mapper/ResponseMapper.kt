package com.example.storyapp.utils.mapper

import com.example.storyapp.source.BaseResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody

object ResponseMapper {

    fun mapErrorResponseToBaseResponse(res: ResponseBody): BaseResponse {
        val gson = Gson()
        val type = object : TypeToken<BaseResponse>() {}.type
        return gson.fromJson(res.charStream(), type)
    }
}