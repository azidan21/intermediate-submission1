package com.dicoding.mystoryapp.Upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystoryapp.data.Response.UploadResponse
import com.dicoding.mystoryapp.data.UserRepository
import kotlinx.coroutines.launch
import java.io.File

class UploadViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _uploadResponse = MutableLiveData<UploadResponse>()
    val uploadResponse: LiveData<UploadResponse> = _uploadResponse

    fun uploadStory(file: File, description: String){
        viewModelScope.launch {
            try {
                val response = userRepository.uploadStory(file, description)
                _uploadResponse.value = response
            } catch (e: Exception){
                _uploadResponse.value = UploadResponse(error = true, message = e.message ?: "Unknown error occurred")
            }
        }
    }
}