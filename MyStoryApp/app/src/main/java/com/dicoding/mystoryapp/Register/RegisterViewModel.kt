package com.dicoding.mystoryapp.Register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystoryapp.data.Response.RegisterResponse
import com.dicoding.mystoryapp.data.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository): ViewModel(){

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            val response = userRepository.userRegister(name, email, password)
            _registerResponse.value = response
        }
    }
}