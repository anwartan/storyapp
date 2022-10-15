package com.example.storyapp

import com.example.storyapp.model.StoryModel
import com.example.storyapp.model.UserModel
import com.example.storyapp.source.remote.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

object DataDummy {
    fun generateDummyBaseResponse():BaseResponse{
        val result = BaseResponse()
        result.error=true
        result.message="Failed"
        return result
    }

    fun generateDummyLoginResponse():LoginResponse{
        return LoginResponse(UserInfoResponse(userId = "1", name = "admin", token = "token"))
    }
    fun generateDummyUserModel():UserModel{
        return UserModel(userId = "1", name = "admin", token = "token"
        )
    }

    fun generateDummyFile(): File {
        return File("/assets/gambar2.PNG")
    }

    fun generateDummyStoryModels():List<StoryModel>{
        val storyModels = ArrayList<StoryModel>()
        for (i in 0..10) {
            val story = StoryModel(
                id = i.toString(),
                name = "Story Name",
                description = "Story Description",
                photoUrl = "https://learnenglishteens.britishcouncil.org/sites/teens/files/b2w_a_short_story_english_exam_1.jpg",
                created = Date(),
                lat = 0.0,
                lon = 0.0
            )
            storyModels.add(story)
        }
        return storyModels
    }

    private fun generateDummyStoryResponses():List<StoryResponse>{
        val storyModels = ArrayList<StoryResponse>()
        for (i in 0..10) {
            val story = StoryResponse(
                id = i.toString(),
                name = "Story Name",
                description = "Story Description",
                photoUrl = "https://learnenglishteens.britishcouncil.org/sites/teens/files/b2w_a_short_story_english_exam_1.jpg",
                lat = 0.0,
                lon = 0.0,
                createdAt = Date()
            )
            storyModels.add(story)
        }
        return storyModels
    }

    fun generateDummyStoriesResponse():StoriesResponse{
        return StoriesResponse(generateDummyStoryResponses())
    }
}