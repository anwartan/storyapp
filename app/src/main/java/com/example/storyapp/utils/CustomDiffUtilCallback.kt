package com.example.storyapp.utils

import androidx.recyclerview.widget.DiffUtil

class CustomDiffUtilCallback<T>(private val old:List<T>, private val new:List<T>) :DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = old[oldItemPosition]!!
        val newItem = new[newItemPosition]!!
        return oldItem::class == newItem::class
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = old[oldItemPosition]
        val newItem = new[newItemPosition]
        return oldItem.hashCode() ==newItem.hashCode()
    }
}