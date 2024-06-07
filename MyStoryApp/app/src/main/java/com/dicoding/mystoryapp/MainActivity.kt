package com.dicoding.mystoryapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mystoryapp.Home.HomeActivity
import com.dicoding.mystoryapp.Register.RegisterActivity
import com.dicoding.mystoryapp.data.Local.UserPreference
import com.dicoding.mystoryapp.data.Local.dataStore
import com.dicoding.mystoryapp.databinding.ActivityMainBinding
import com.dicoding.mystoryapp.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        checkSession()
        setupAction()
    }

    private fun checkSession() {
        val userPreference = UserPreference.getInstance(dataStore)
        CoroutineScope(Dispatchers.Main).launch {
            if (userPreference.isSessionActive()) {
                showLoading(false)
                goToHomeActivity()
            }
        }
    }

    private fun setupAction(){
        showLoading(false)
        binding.loginButton.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.signupButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun goToHomeActivity(){
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}