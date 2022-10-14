package com.example.storyapp.register

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.base.BaseActivity
import com.example.storyapp.data.Result
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.helper.ViewModelFactory
import com.example.storyapp.login.LoginActivity


class RegisterActivity : BaseActivity() {
    private lateinit var binding:ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerViewModel = ViewModelProvider(this,
            ViewModelFactory(this)
        )[RegisterViewModel::class.java]


        binding.btnRegister.setOnClickListener {
            val email = binding.emailEditText
            val password = binding.passwordEditText
            val name = binding.etName
            if(email.isValid() && password.isValid() && name.isValid()){
                registerViewModel.register(email.text.toString(), name.text.toString(),password.text.toString())
                    .observe(this){
                        when(it){
                            is Result.Loading->{
                                showLoading(true)
                            }
                            is Result.Success->{
                                showLoading(false)
                                val intent = Intent(this, LoginActivity::class.java)
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