package com.example.storyapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.databinding.ItemListStoryBinding
import com.example.storyapp.model.StoryModel

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    var onItemClick: ((StoryModel,ImageView,TextView,TextView) -> Unit)? = null
    private val listData =ArrayList<StoryModel>()
    @SuppressLint("NotifyDataSetChanged")
    fun setListData(newListData:List<StoryModel>?){
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        private val binding= ItemListStoryBinding.bind(itemView)
        fun bind (data: StoryModel){
            binding.tvTitle.text=data.name
            binding.tvDescription.text=data.description
            Glide.with(itemView.context)
                .load(data.photoUrl)
                .into(binding.ivPoster)

        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition],binding.ivPoster,binding.tvTitle,binding.tvDescription)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_story,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}