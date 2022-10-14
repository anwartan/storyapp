package com.example.storyapp.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.data.AuthRepository
import com.example.storyapp.data.Result
import com.example.storyapp.utils.LiveDataTestUtil.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var authRepository: AuthRepository
    private lateinit var registerViewModel: RegisterViewModel
    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(authRepository)
    }
    @Test
    fun `when Register Is Fetching Should Return Loading`() {
        val email="admin@gmail.com"
        val name="admin"
        val password="admin"
        val expected = MutableLiveData<Result<Boolean>>()
        expected.value = Result.Loading
        `when`(authRepository.register(email, name, password)).thenReturn(expected)
        val actual = registerViewModel.register(email, name, password).getOrAwaitValue()
        Mockito.verify(authRepository).register(email, name, password)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Loading)
    }
    @Test
    fun `when Register Is Successfully Should Return Success and Value True`() {
        val email="admin@gmail.com"
        val name="admin"
        val password="admin"
        val expected = MutableLiveData<Result<Boolean>>()
        expected.value = Result.Success(true)
        `when`(authRepository.register(email, name, password)).thenReturn(expected)
        val actual = registerViewModel.register(email, name, password).getOrAwaitValue()
        Mockito.verify(authRepository).register(email, name, password)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
        Assert.assertEquals(true, (actual as Result.Success).data)
    }
    @Test
    fun `when Register Is Failed Should Return Error with Message`() {
        val email="admin@gmail.com"
        val name="admin"
        val password="admin"
        val expected = MutableLiveData<Result<Boolean>>()
        expected.value = Result.Error("FAILED REGISTER")
        `when`(authRepository.register(email, name, password)).thenReturn(expected)
        val actual = registerViewModel.register(email, name, password).getOrAwaitValue()
        Mockito.verify(authRepository).register(email, name, password)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Error)
        Assert.assertEquals("FAILED REGISTER",(actual as Result.Error).error)
    }
}