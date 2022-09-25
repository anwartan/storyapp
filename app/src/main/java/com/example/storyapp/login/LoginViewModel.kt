package com.example.storyapp.login

import androidx.lifecycle.*
import com.example.storyapp.event.Event
import com.example.storyapp.model.UserModel
import com.example.storyapp.model.UserPreference
import com.example.storyapp.source.ApiConfig
import com.example.storyapp.source.LoginResponse
import com.example.storyapp.utils.mapper.ResponseMapper
import com.example.storyapp.utils.mapper.UserMapper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun login(email: String, password: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                showLoading(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    viewModelScope.launch {
                        pref.login(UserMapper.mapResponseToDomain(responseBody.loginResult))
                    }
                } else {
                    val message = response.errorBody()
                        ?.let { ResponseMapper.mapErrorResponseToBaseResponse(it).message }
                        ?: "ERROR"
                    _message.value = Event(message)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showLoading(false)
                _message.value = Event(t.message ?: "ERROR")
            }

        })
    }

    internal fun showLoading(b: Boolean) {
        _isLoading.value = b
    }

}