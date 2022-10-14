package com.example.storyapp.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "story")
data class StoryEntity (
    @PrimaryKey
    @field:SerializedName("id")
    val id:String,
    @field:SerializedName("name")
    val name:String,
    @field:SerializedName("description")
    val description:String,
    @field:SerializedName("photoUrl")
    val photoUrl:String,
    @field:SerializedName("created")
    val created: Date,
    @field:SerializedName("lat")
    val lat: Double,
    @field:SerializedName("lon")
    val lon: Double
)