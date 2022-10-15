package com.example.storyapp.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapp.DataDummy
import com.example.storyapp.model.UserModel
import com.example.storyapp.model.UserPreference
import com.example.storyapp.utils.LiveDataTestUtil.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
class MainViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userPreference: UserPreference
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp(){
        mainViewModel = MainViewModel(userPreference)
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
    fun `when Get User Is Successfully Should Return User Model with Empty Token`() {

        val expected : Flow<UserModel> = flow {
            emit(UserModel("","",""))
        }
        Mockito.`when`(userPreference.getUser()).thenReturn(expected)
        val actual = mainViewModel.getUser().getOrAwaitValue()
        Mockito.verify(userPreference).getUser()
        Assert.assertNotNull(actual)
        Assert.assertEquals("",actual.token)
    }

    @Test
    fun `when Get User Is Successfully Should Return User Model with Token`() {

        val expected : Flow<UserModel> = flow {
            emit(DataDummy.generateDummyUserModel())
        }
        Mockito.`when`(userPreference.getUser()).thenReturn(expected)
        val actual = mainViewModel.getUser().getOrAwaitValue()
        Mockito.verify(userPreference).getUser()
        Assert.assertNotNull(actual)
        Assert.assertEquals("token",actual.token)
    }

}