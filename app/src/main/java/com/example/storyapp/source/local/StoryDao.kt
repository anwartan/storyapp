package com.example.storyapp.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(storyEntity: List<StoryEntity>)
    @Query("SELECT * FROM story")
    fun getAllQuote(): PagingSource<Int, StoryEntity>
    @Query("DELETE FROM story")
    suspend fun deleteAll()
}