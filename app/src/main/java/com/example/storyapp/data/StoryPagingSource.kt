package com.example.storyapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storyapp.model.StoryModel
import com.example.storyapp.source.remote.ApiService
import com.example.storyapp.utils.mapper.StoryMapper

class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int,StoryModel>() {
    override fun getRefreshKey(state: PagingState<Int, StoryModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryModel> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.findStories(position, params.loadSize,"1").listStory
            val storyModels = StoryMapper.mapResponsesToDomains(responseData)
            LoadResult.Page(
                data = storyModels,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (storyModels.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}