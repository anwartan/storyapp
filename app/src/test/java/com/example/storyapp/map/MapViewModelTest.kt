package com.example.storyapp.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.DataDummy
import com.example.storyapp.data.Result
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.model.StoryModel
import com.example.storyapp.utils.LiveDataTestUtil.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MapViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapViewModel: MapViewModel
    private var dummyData = DataDummy.generateDummyStoryModels()
    @Before
    fun setUp(){
        mapViewModel = MapViewModel(storyRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when Get Story Is Fetching Should Return Loading`() {

        val expected = MutableLiveData<Result<List<StoryModel>>>()
        expected.value = Result.Loading
        Mockito.`when`(storyRepository.getDataStories()).thenReturn(expected)
        val actual = mapViewModel.getStories().getOrAwaitValue()
        Mockito.verify(storyRepository).getDataStories()
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Loading)
    }

    @Test
    fun `when Get Story Is Successfully Should Return Success and List User Models`() {

        val expected = MutableLiveData<Result<List<StoryModel>>>()
        expected.value = Result.Success(dummyData)
        Mockito.`when`(storyRepository.getDataStories()).thenReturn(expected)
        val actual = mapViewModel.getStories().getOrAwaitValue()
        Mockito.verify(storyRepository).getDataStories()
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
        Assert.assertEquals(dummyData,(actual as Result.Success).data)

    }
    @Test
    fun `when Get Story Is Failed Should Return Error`() {
        val expected = MutableLiveData<Result<List<StoryModel>>>()
        expected.value = Result.Error("Failed Get Story")
        Mockito.`when`(storyRepository.getDataStories()).thenReturn(expected)
        val actual = mapViewModel.getStories().getOrAwaitValue()
        Mockito.verify(storyRepository).getDataStories()
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Error)
        Assert.assertEquals("Failed Get Story",(actual as Result.Error).error)
    }
}