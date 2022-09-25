package com.example.storyapp.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.base.BaseActivity
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.helper.ViewModelFactory
import com.example.storyapp.login.LoginActivity
import com.example.storyapp.model.UserPreference

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : BaseActivity() {
    private lateinit var binding:ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerViewModel = ViewModelProvider(this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[RegisterViewModel::class.java]


        binding.btnRegister.setOnClickListener {
            val email = binding.emailEditText
            val password = binding.passwordEditText
            val name = binding.etName
            if(email.isValid() && password.isValid() && name.isValid()){
                registerViewModel.register(email.text.toString(), name.text.toString(),password.text.toString())
            }
        }

        registerViewModel.isSignUp.observe(this){
            if(it==true){
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
        registerViewModel.isLoading.observe(this){
            showLoading(it)
        }
        registerViewModel.message.observe(this){
            it.getContentIfNotHandled()?.let { message ->
                showToast(message)
            }
        }

    }
}