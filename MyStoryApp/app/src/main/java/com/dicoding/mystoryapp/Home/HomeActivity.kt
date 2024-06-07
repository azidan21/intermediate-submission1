package com.dicoding.mystoryapp.Home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mystoryapp.Adapter.StoryAdapter
import com.dicoding.mystoryapp.Factory.ViewModelFactory
import com.dicoding.mystoryapp.MainActivity
import com.dicoding.mystoryapp.R
import com.dicoding.mystoryapp.Upload.UploadActivity
import com.dicoding.mystoryapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val viewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: HomeViewModel by viewModels{viewModelFactory}

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        viewModel.storiesResponse.observe(this){storiesResponse ->
            adapter = StoryAdapter(storiesResponse)
            binding.recyclerView.adapter = adapter
        }
        viewModel.isLoading.observe(this){
            showLoading(it)
        }


        viewModel.getStories()
        setAppBar()
        setAddButton()
    }

    private fun setAppBar(){
        val viewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: HomeViewModel by viewModels{viewModelFactory}
        binding.topAppBar.setOnMenuItemClickListener{menuItem->
            when(menuItem.itemId){
                R.id.logout -> {
                    viewModel.userLogout()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    true
                }
                else-> false

            }
        }
    }

    private fun setAddButton(){
        binding.addButton.setOnClickListener {
            startActivity(Intent(this, UploadActivity::class.java))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}