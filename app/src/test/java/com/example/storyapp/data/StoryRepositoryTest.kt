package com.example.storyapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapp.DataDummy
import com.example.storyapp.source.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
    fun `when Get Stories Should Return Stories Response`()  = runTest {
        val expectedStory = DataDummy.generateDummyStoriesResponse()
        val actualStory = apiService.getStories(5,1,"1")
        Assert.assertNotNull(actualStory)
        Assert.assertEquals(expectedStory.error, actualStory.error)
        Assert.assertEquals(expectedStory.listStory, actualStory.listStory)
    }
    @Test
    fun `when Add Stories Should Return Base Response`()  = runTest {
        val expectedStory = DataDummy.generateDummyBaseResponse()
        val dummyFile = DataDummy.generateDummyFile()
        val requestImageFile = dummyFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multiPart = MultipartBody.Part.createFormData("photo", dummyFile.name, requestImageFile)
        val descriptionRequest = "description".toRequestBody("text/plain".toMediaType())
        val latRequest = 0.0.toString().toRequestBody("text/plain".toMediaType())
        val lonRequest = 0.0.toString().toRequestBody("text/plain".toMediaType())
        val actualStory = apiService.addStory(descriptionRequest,multiPart,latRequest,lonRequest)
        Assert.assertNotNull(actualStory)
        Assert.assertEquals(expectedStory.error, actualStory.error)

    }
    @Test
    fun `when Find Stories Should Return Stories Response`()  = runTest {
        val expectedStory = DataDummy.generateDummyStoriesResponse()
        val actualStory = apiService.findStories(1,1,"1")
        Assert.assertNotNull(actualStory)
        Assert.assertEquals(expectedStory.error, actualStory.error)
        Assert.assertEquals(expectedStory.listStory.size, actualStory.listStory.size)
    }

}