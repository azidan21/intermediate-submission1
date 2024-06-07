package com.dicoding.mystoryapp.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mystoryapp.Factory.ViewModelFactory
import com.dicoding.mystoryapp.Home.HomeActivity
import com.dicoding.mystoryapp.data.Local.UserModel
import com.dicoding.mystoryapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAction()
    }

    private fun setAction(){
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString().length
            val password = binding.edLoginPassword.text.toString().length
            if (email != 0 && password != 0){
                setLogin()
            }else{
                showToast("Isi data dengan benar!")
            }
        }
    }

    private fun setLogin(){
        val email = binding.edLoginEmail.text.toString().trim()
        val password = binding.edLoginPassword.text.toString()
        showLoading(true)
        val viewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: LoginViewModel by viewModels{viewModelFactory}

        viewModel.loginUser(email, password)
        viewModel.loginResponse.observe(this){ response ->
            if (response.error == false){
                val userId = response.loginResult?.userId.toString()
                val token = response.loginResult?.token.toString()
                val name = response.loginResult?.name.toString()
                viewModel.saveSession(UserModel(
                    userId, name, token
                ))
                showLoading(false)
                startActivity(Intent(this, HomeActivity::class.java))
                Log.e("message", "${response.loginResult}")
                Log.e("message", "${response.message}")
            }else{
                showLoading(false)
                showToast("Error, Periksa kembali email atau Password")
                Log.e("message", "${response.loginResult}")
                Log.e("message", "${response.message}")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}