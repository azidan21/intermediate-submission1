package com.dicoding.mystoryapp.Upload

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mystoryapp.Factory.ViewModelFactory
import com.dicoding.mystoryapp.Home.HomeActivity
import com.dicoding.mystoryapp.R
import com.dicoding.mystoryapp.Utils.reduceFileImage
import com.dicoding.mystoryapp.Utils.uriToFile
import com.dicoding.mystoryapp.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButton()
    }

    private fun setButton(){
        binding.intentGallery.setOnClickListener {
            startGallery()
        }
        binding.buttonAdd.setOnClickListener {
            uploadStory()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage(){
        currentImageUri?.let {
            Log.d("Image", "showImage: $it")
            binding.ivUploadPhoto.setImageURI(it)
        }
    }

    private fun uploadStory(){
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val description = binding.edAddDescription.text.toString()

            showLoading(true)

            val viewModelFactory = ViewModelFactory.getInstance(this)
            val viewModel: UploadViewModel by viewModels{viewModelFactory}

            viewModel.uploadStory(imageFile, description)

            viewModel.uploadResponse.observe(this){ uploadResponse->
                if (uploadResponse.error == false){
                    showLoading(false)
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }else{
                    showLoading(false)
                    showToast(uploadResponse.message.toString())
                }
            }


        } ?: showToast(getString(R.string.empty_image_warning))
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