package com.dicoding.mystoryapp.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mystoryapp.DetailActivity.DetailActivity
import com.dicoding.mystoryapp.data.Response.ListStoryItem
import com.dicoding.mystoryapp.databinding.ItemStoryBinding

class StoryAdapter(private val stories: List<ListStoryItem>) :RecyclerView.Adapter<StoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = stories[position]
        holder.bind(story)

    }

    override fun getItemCount(): Int = stories.size

    inner class MyViewHolder(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem){
            binding.tvItemName.text = story.name
            Glide.with(itemView)
                .load(story.photoUrl)
                .into(binding.ivItemPhoto)

            itemView.setOnClickListener {
                val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                intentDetail.putExtra("name", story.name)
                intentDetail.putExtra("image", story.photoUrl)
                intentDetail.putExtra("description", story.description)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivItemPhoto, "image"),
                        Pair(binding.tvItemName, "name")
                    )
                itemView.context.startActivity(intentDetail, optionsCompat.toBundle())
            }
        }
    }
}