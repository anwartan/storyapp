package com.example.storyapp

import com.example.storyapp.source.local.StoryEntity
import java.util.*

object DataDummy {
    fun generateDummyStoryEntities():List<StoryEntity>{
        val storyEntity = ArrayList<StoryEntity>()
        for (i in 0..10) {
            storyEntity.add(StoryEntity(i.toString(),"story","description","https://learnenglishteens.britishcouncil.org/sites/teens/files/b2w_a_short_story_english_exam_1.jpg",Date(),1.0,1.0))
        }
        return storyEntity
    }
}