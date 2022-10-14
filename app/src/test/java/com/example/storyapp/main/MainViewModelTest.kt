package com.example.storyapp.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapp.DataDummy
import com.example.storyapp.model.UserModel
import com.example.storyapp.model.UserPreference
import com.example.storyapp.utils.LiveDataTestUtil.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
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
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }
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