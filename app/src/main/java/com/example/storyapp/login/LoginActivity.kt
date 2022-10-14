package com.example.storyapp.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.base.BaseActivity
import com.example.storyapp.data.Result
import com.example.storyapp.dataStore
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.helper.ViewModelFactory
import com.example.storyapp.main.MainActivity
import com.example.storyapp.model.UserPreference
import com.example.storyapp.register.RegisterActivity
import kotlinx.coroutines.runBlocking

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val pref= UserPreference.getInstance(this.dataStore)
        loginViewModel = ViewModelProvider(this, ViewModelFactory(this))[LoginViewModel::class.java]
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
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
        binding.btnLogin.setOnClickListener {

            val email = binding.emailEditText
            val password = binding.passwordEditText
            if (email.isValid() && password.isValid()) {
                loginViewModel.login(email.text.toString(), password.text.toString()).observe(this)
                {
                    when(it){
                        is Result.Loading->{
                            showLoading(true)
                        }
                        is Result.Success->{

                            showLoading(false)
                            runBlocking {
                                pref.login(it.data)
                            }
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        is Result.Error->{
                            showLoading(false)
                            showToast(it.error)
                        }
                    }
                }
            }
        }
    }
}