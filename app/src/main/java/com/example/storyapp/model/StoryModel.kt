package com.example.storyapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class StoryModel(
    val id :String,
    val name:String,
    val description:String,
    val photoUrl:String,
    val created:Date,
    val lat: Float,
    val lon: Float,
):Parcelable