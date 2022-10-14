package com.example.storyapp.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.DataDummy
import com.example.storyapp.data.AuthRepository
import com.example.storyapp.data.Result
import com.example.storyapp.model.UserModel
import com.example.storyapp.model.UserPreference
import com.example.storyapp.utils.LiveDataTestUtil.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
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
class LoginViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var authRepository: AuthRepository
    private lateinit var loginViewModel: LoginViewModel
    @Mock
    private lateinit var userPreference: UserPreference
    private var dummyUserModel = DataDummy.generateDummyUserModel()
    @Before
    fun setUp(){
        loginViewModel = LoginViewModel(authRepository,userPreference)
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
    fun `when Login Is Fetching Should Return Loading`() {
        val email="admin@gmail.com"
        val password="admin"
        val expected = MutableLiveData<Result<UserModel>>()
        expected.value = Result.Loading
        Mockito.`when`(authRepository.login(email,  password)).thenReturn(expected)
        val actual = loginViewModel.login(email,  password).getOrAwaitValue()
        Mockito.verify(authRepository).login(email,  password)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Loading)
    }
    @Test
    fun `when Login Is Successfully Should Return Success`() {
        val email="admin@gmail.com"
        val password="admin"
        val expected = MutableLiveData<Result<UserModel>>()
        expected.value = Result.Success(dummyUserModel)
        Mockito.`when`(authRepository.login(email,  password)).thenReturn(expected)
        val actual = loginViewModel.login(email,  password).getOrAwaitValue()
        Mockito.verify(authRepository).login(email,  password)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
        Assert.assertEquals(dummyUserModel,(actual as Result.Success).data)
    }
    @Test
    fun `when Login Is Failed Should Return Error`() {
        val email="admin@gmail.com"
        val password="admin"
        val expected = MutableLiveData<Result<UserModel>>()
        expected.value = Result.Error("Failed Login")
        Mockito.`when`(authRepository.login(email,  password)).thenReturn(expected)
        val actual = loginViewModel.login(email,  password).getOrAwaitValue()
        Mockito.verify(authRepository).login(email,  password)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Error)
        Assert.assertEquals("Failed Login",(actual as Result.Error).error)
    }

    @Test
    fun `when Get User Should Return User Model and Token is empty`() {

        val expected : Flow<UserModel> =flow {
            emit(UserModel("","",""))
        }
        Mockito.`when`(userPreference.getUser()).thenReturn(expected)
        val actual = loginViewModel.getUser().getOrAwaitValue()
        Mockito.verify(userPreference).getUser()
        Assert.assertNotNull(actual)
        Assert.assertEquals("",actual.token)
    }

    @Test
    fun `when Get User Should Return User Model and Token is not empty`() {

        val expected : Flow<UserModel> =flow {
            emit(UserModel("admin","1","token"))
        }
        Mockito.`when`(userPreference.getUser()).thenReturn(expected)
        val actual = loginViewModel.getUser().getOrAwaitValue()
        Mockito.verify(userPreference).getUser()
        Assert.assertNotNull(actual)
        Assert.assertEquals("token",actual.token)
    }
}

