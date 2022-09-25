package com.example.storyapp.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.base.BaseActivity
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.helper.ViewModelFactory
import com.example.storyapp.main.MainActivity
import com.example.storyapp.model.UserPreference
import com.example.storyapp.register.RegisterActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this,ViewModelFactory(UserPreference.getInstance(dataStore)))[LoginViewModel::class.java]
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {

            val email = binding.emailEditText
            val password = binding.passwordEditText
            if(email.isValid() && password.isValid()){
                loginViewModel.login(email.text.toString(),password.text.toString())
            }
        }

        loginViewModel.getUser().observe(this){
            if(it.token.isNotEmpty()){
                val intent = Intent(this, MainActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()

            }
        }
        loginViewModel.isLoading.observe(this){
            showLoading(it)
        }
        loginViewModel.message.observe(this){
            it.getContentIfNotHandled()?.let { message ->
                showToast(message)
            }
        }

    }
}