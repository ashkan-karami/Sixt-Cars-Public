package com.example.sixtcar.presentation.base.recyclerview

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

@Suppress("LeakingThis")
abstract class BaseAdapter<T, E: BaseVH<T>>(differCallback: DiffUtil.ItemCallback<T>):
    ListAdapter<T, E>(differCallback) {
    
    private val differ = AsyncListDiffer(this, differCallback)
    
    override fun getItemCount(): Int = differ.currentList.size
    
    fun setData(items: List<T>) {
        differ.submitList(items)
    }
    
    fun onClearItems() {
        differ.submitList(mutableListOf())
    }
    
    override fun onBindViewHolder(holder: E, position: Int) {
        holder.bind(differ.currentList[position], position)
    }
}