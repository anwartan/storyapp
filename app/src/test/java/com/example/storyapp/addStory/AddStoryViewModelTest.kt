package com.example.storyapp.addStory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.DataDummy
import com.example.storyapp.data.Result
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.utils.LiveDataTestUtil.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var addStoryViewModel: AddStoryViewModel
    @Before
    fun setUp() {
        addStoryViewModel = AddStoryViewModel(storyRepository)
    }
    @Test
    fun `when Add Story Is Fetching Should Return Loading`() {
        val description = "description"
        val lat = 1F
        val lon = 2F
        val file = DataDummy.generateDummyFile()
        val expected = MutableLiveData<Result<Boolean>>()
        expected.value = Result.Loading
        Mockito.`when`(storyRepository.addStory(description,lat,lon,file)).thenReturn(expected)
        val actual = addStoryViewModel.addStory(description,lat,lon,file).getOrAwaitValue()
        Mockito.verify(storyRepository).addStory(description,lat,lon,file)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Loading)
    }

    @Test
    fun `when Add Story Is Successfully Should Return Success`() {
        val description = "description"
        val lat = 1F
        val lon = 2F
        val file = DataDummy.generateDummyFile()
        val expected = MutableLiveData<Result<Boolean>>()
        expected.value = Result.Success(true)
        Mockito.`when`(storyRepository.addStory(description,lat,lon,file)).thenReturn(expected)
        val actual = addStoryViewModel.addStory(description,lat,lon,file).getOrAwaitValue()
        Mockito.verify(storyRepository).addStory(description,lat,lon,file)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
        Assert.assertEquals(true, (actual as Result.Success).data)
    }
    @Test
    fun `when Add Story Is Failed Should Return Error`() {
        val description = "description"
        val lat = 1F
        val lon = 2F
        val file = DataDummy.generateDummyFile()
        val expected = MutableLiveData<Result<Boolean>>()
        expected.value = Result.Error("Failed Add Story")
        Mockito.`when`(storyRepository.addStory(description,lat,lon,file)).thenReturn(expected)
        val actual = addStoryViewModel.addStory(description,lat,lon,file).getOrAwaitValue()
        Mockito.verify(storyRepository).addStory(description,lat,lon,file)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Error)
        Assert.assertEquals("Failed Add Story", (actual as Result.Error).error)
    }
}