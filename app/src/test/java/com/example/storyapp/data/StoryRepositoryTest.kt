package com.example.storyapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapp.DataDummy
import com.example.storyapp.source.remote.ApiService
import com.example.storyapp.utils.LiveDataTestUtil.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*

@ExperimentalCoroutinesApi
class StoryRepositoryTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var apiService: ApiService
    private lateinit var storyRepository: StoryRepository

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }
    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }
    @Before
    fun setUp(){

        apiService = FakeApiService()
        storyRepository = StoryRepository(apiService)
    }

    @Test
    fun `when Add Stories Should Return Base Response`()  = runTest {
        val dummyFile = DataDummy.generateDummyFile()
        val actual  = storyRepository.addStory("description",0.0F,0.0F,dummyFile)
        Assert.assertNotNull(actual)
        Assert.assertEquals(Result.Loading, actual.getOrAwaitValue())
        Assert.assertEquals(true, ((actual.getOrAwaitValue()) as Result.Success).data)
    }
    @Test
    fun `when Find Stories Should Return Stories Response`()  = runTest {
        val expectedStory = DataDummy.generateDummyStoriesResponse()
        val actualStory = storyRepository.getDataStories()
        Assert.assertNotNull(actualStory)
        Assert.assertEquals(Result.Loading, actualStory.getOrAwaitValue())
        Assert.assertEquals(expectedStory.listStory.size, ((actualStory.getOrAwaitValue()) as Result.Success).data.size)

    }

}