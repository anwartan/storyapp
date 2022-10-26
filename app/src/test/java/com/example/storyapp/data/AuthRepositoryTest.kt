package com.example.storyapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapp.DataDummy
import com.example.storyapp.model.UserPreference
import com.example.storyapp.source.remote.ApiService
import com.example.storyapp.utils.LiveDataTestUtil.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var apiService:ApiService
    @Mock
    private lateinit var userPreference: UserPreference
    @InjectMocks
    private lateinit var authRepository: AuthRepository
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

    }
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun login() = runTest{
        val expectResult = DataDummy.generateDummyLoginResponse()
        val email="admin@gmail.com"
        val password="123456"
        Mockito.`when`(apiService.login(email,password)).thenReturn(expectResult)
        val actual  = authRepository.login(email, password)
        Assert.assertTrue(actual.getOrAwaitValue() is Result.Loading)
        val actualResult = actual.getOrAwaitValue()
        Assert.assertTrue(actualResult is Result.Success)

        Assert.assertEquals(expectResult.loginResult.userId,(actualResult as Result.Success).data.userId)
    }

    @Test
    fun register() = runTest{
        val expectResult = DataDummy.generateDummyBaseResponse()
        val email="admin@gmail.com"
        val password="123456"
        val name="admin"
        Mockito.`when`(apiService.register(email,name,password)).thenReturn(expectResult)
        val actual  = authRepository.register(email, name,password)
        Assert.assertTrue(actual.getOrAwaitValue() is Result.Loading)
        val actualResult = actual.getOrAwaitValue()
        Assert.assertTrue(actualResult is Result.Success)

        Assert.assertEquals(true,(actualResult as Result.Success).data)
    }

}