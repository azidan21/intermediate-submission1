package com.dicoding.mystoryapp.DetailActivity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.mystoryapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDetail()
        animation()
    }

    private fun setDetail(){
        val imageUrl = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        Glide.with(this)
            .load(imageUrl)
            .into(binding.ivDetailPhoto)
        binding.tvDetailName.setText(name)
        binding.tvDetailDescription.setText(description)
    }

    private fun animation(){
        val desc = ObjectAnimator.ofFloat(binding.tvDetailDescription, View.ALPHA, 1f)
            .setDuration(100)

        AnimatorSet().apply{
            play(desc)
            startDelay = 500
            start()
        }

    }

}