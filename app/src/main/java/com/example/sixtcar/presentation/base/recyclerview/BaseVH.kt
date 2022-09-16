package com.example.sixtcar.presentation.base.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseVH<T>(b: ViewBinding): RecyclerView.ViewHolder(b.root)
{
    abstract fun bind(item: T, position:Int)
}