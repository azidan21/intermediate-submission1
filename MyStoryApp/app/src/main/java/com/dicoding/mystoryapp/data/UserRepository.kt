package com.dicoding.mystoryapp.data

import com.dicoding.mystoryapp.data.Local.UserModel
import com.dicoding.mystoryapp.data.Local.UserPreference
import com.dicoding.mystoryapp.data.Response.LoginResponse
import com.dicoding.mystoryapp.data.api.ApiService
import com.dicoding.mystoryapp.data.Response.RegisterResponse
import com.dicoding.mystoryapp.data.Response.StoryResponse
import com.dicoding.mystoryapp.data.Response.UploadResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class UserRepository(private val apiService: ApiService, private val userPreference: UserPreference){

    companion object {
        private var instance: UserRepository? = null
        fun getInstance(apiService: ApiService, userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }

    suspend fun userRegister(name: String, email: String, password: String): RegisterResponse {
        return try{
            apiService.register(name, email, password)
        } catch (e: Exception) {
            RegisterResponse(error = true, message = e.message)
        }
    }

    suspend fun userLogin(email: String, password: String): LoginResponse{
        return apiService.login(email, password)
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    suspend fun userLogout(){
        userPreference.logout()
    }

    suspend fun getStories(): StoryResponse {
        return apiService.getStories()
    }

    suspend fun uploadStory(file: File, description: String): UploadResponse{
        val requestFile = file.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("photo", file.name, requestFile)
        val descriptionBody = description.toRequestBody("text/plain".toMediaType())

        return apiService.uploadStory(multipartBody, descriptionBody)
    }
}