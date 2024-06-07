package com.dicoding.mystoryapp.Register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mystoryapp.Factory.ViewModelFactory
import com.dicoding.mystoryapp.MainActivity
import com.dicoding.mystoryapp.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setAction()
    }

    private fun setAction(){
        binding.registerButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString().length
            val email = binding.edRegisterEmail.text.toString().length
            val password = binding.edRegisterPassword.text.toString().length
            if (name != 0 && email != 0 && password != 0){
                setRegister()
            }else{
                showToast("Isi data dengan benar!")
            }

        }
    }

    private fun setRegister(){
        val name = binding.edRegisterName.text.toString().trim()
        val email = binding.edRegisterEmail.text.toString().trim()
        val password = binding.edRegisterPassword.text.toString()
        showLoading(true)
        val viewModelFactory = ViewModelFactory.getInstance(this@RegisterActivity)
        val viewModel: RegisterViewModel by viewModels{viewModelFactory}

        viewModel.registerUser(name, email, password)
        viewModel.registerResponse.observe(this) { response->
            if (response.error == false){
                showLoading(false)
                showToast("Pendaftaran Berhasil")
                startActivity(Intent(this, MainActivity::class.java))
            }else {
                showLoading(false)
                showToast("Pendaftaran Gagal")
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