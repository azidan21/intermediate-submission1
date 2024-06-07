package com.dicoding.mystoryapp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystoryapp.data.Local.UserModel
import com.dicoding.mystoryapp.data.Response.LoginResponse
import com.dicoding.mystoryapp.data.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun loginUser(email: String, password: String){
        viewModelScope.launch {
            val response = userRepository.userLogin(email, password)
            _loginResponse.value = response
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }

}