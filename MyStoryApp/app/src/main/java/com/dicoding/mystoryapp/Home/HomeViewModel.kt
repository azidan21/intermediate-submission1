package com.dicoding.mystoryapp.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystoryapp.data.Response.ListStoryItem
import com.dicoding.mystoryapp.data.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository):ViewModel() {

    private val _storiesResponse = MutableLiveData<List<ListStoryItem>>()
    val storiesResponse: LiveData<List<ListStoryItem>> = _storiesResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getStories(){
        _isLoading.value = true
        viewModelScope.launch {
            _storiesResponse.value = userRepository.getStories().listStory
        }
        _isLoading.value = false
    }
     fun userLogout(){
         viewModelScope.launch {
            userRepository.userLogout()
         }
     }
}