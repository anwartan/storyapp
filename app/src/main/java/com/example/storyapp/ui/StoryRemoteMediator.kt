package com.example.storyapp.ui

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.storyapp.model.StoryModel
import com.example.storyapp.source.local.StoryDatabase
import com.example.storyapp.source.remote.ApiService
import com.example.storyapp.utils.mapper.StoryMapper

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(private val apiService: ApiService,private val database: StoryDatabase):RemoteMediator<Int,StoryModel>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StoryModel>
    ): MediatorResult {
        val page = INITIAL_PAGE_INDEX
        return try {
            val responseData = apiService.findStories(page, state.config.pageSize,"1")
            val endOfPaginationReached = responseData.listStory.isEmpty()
            val listStoryEntity =StoryMapper.mapResponsesToEntities(responseData.listStory)
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.storyDao().deleteAll()
                }
                database.storyDao().insertStory(listStoryEntity)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}