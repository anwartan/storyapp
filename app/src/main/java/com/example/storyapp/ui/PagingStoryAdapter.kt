package com.example.storyapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.ItemListStoryBinding
import com.example.storyapp.model.StoryModel


class PagingStoryAdapter:PagingDataAdapter<StoryModel,PagingStoryAdapter.ViewHolder>(
    DIFF_CALLBACK) {

    var onItemClick: ((StoryModel?, ImageView, TextView, TextView) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemListStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StoryModel) {
            binding.tvTitle.text = data.name
            binding.tvDescription.text = data.description
            Glide.with(itemView.context)
                .load(data.photoUrl)
                .into(binding.ivPoster)
        }
        init{
            binding.root.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition),binding.ivPoster,binding.tvTitle,binding.tvDescription)
            }
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryModel>() {
            override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }



}