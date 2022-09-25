package com.example.storyapp.source

import com.google.gson.annotations.SerializedName
import java.util.*

data class StoryResponse(

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("createdAt")
	val createdAt: Date,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description:String,

	@field:SerializedName("lon")
	val lon: Float,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("lat")
	val lat: Float
)
