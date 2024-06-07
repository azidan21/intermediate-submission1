package com.dicoding.mystoryapp.Factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mystoryapp.Home.HomeViewModel
import com.dicoding.mystoryapp.Register.RegisterViewModel
import com.dicoding.mystoryapp.Upload.UploadViewModel
import com.dicoding.mystoryapp.data.UserRepository
import com.dicoding.mystoryapp.di.Injection
import com.dicoding.mystoryapp.login.LoginViewModel

class ViewModelFactory private constructor(private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory()  {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { INSTANCE = it }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(UploadViewModel::class.java)){
            return UploadViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}