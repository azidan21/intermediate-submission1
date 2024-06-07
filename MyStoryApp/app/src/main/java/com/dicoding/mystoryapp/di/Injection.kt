package com.dicoding.mystoryapp.di

import android.content.Context
import com.dicoding.mystoryapp.data.Local.UserPreference
import com.dicoding.mystoryapp.data.Local.dataStore
import com.dicoding.mystoryapp.data.UserRepository
import com.dicoding.mystoryapp.data.api.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(pref)
        return UserRepository.getInstance(apiService, pref)
    }
}