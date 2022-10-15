package com.example.storyapp.source.local

import androidx.lifecycle.LiveData
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
    fun getAllStory(): PagingSource<Int, StoryEntity>
    @Query("DELETE FROM story")
    suspend fun deleteAll()
    @Query("SELECT * FROM story")
    fun findAll(): LiveData<List<StoryEntity>>
}