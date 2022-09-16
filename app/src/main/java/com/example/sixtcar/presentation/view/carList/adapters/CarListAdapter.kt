package com.example.sixtcar.presentation.view.carList.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.sixtcar.R
import com.example.sixtcar.databinding.AdapterCarListBinding
import com.example.sixtcar.presentation.base.recyclerview.BaseAdapter
import com.example.sixtcar.presentation.base.recyclerview.BaseVH
import com.example.sixtcar.presentation.view.carList.models.CarItem

/**
 *  This adapter will render data into the recycler view by inflating view by using of ViewBinding.
 */
class CarListAdapter(private val onItemClickListener: (view: View, position: Int) -> Unit) :
    BaseAdapter<CarItem, CarListAdapter.CarViewHolder>(differCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder =
        CarViewHolder(
            AdapterCarListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    inner class CarViewHolder(private val binding: AdapterCarListBinding) :
        BaseVH<CarItem>(binding) {

        override fun bind(item: CarItem, position: Int) {
            with(binding) {
                rootAdapterCarItem.setOnClickListener {
                    onItemClickListener.invoke(it, adapterPosition)
                }
                tvAdapterCarName.text = item.name
                tvAdapterCarModel.text = item.modelName
                val details = "${item.group}  |  ${item.series}  |  ${item.color}"
                tvAdapterCarDetails.text = details
                try {
                    Glide.with(root.context)
                        .load(item.icon)
                        .placeholder(R.drawable.ic_car_placeholder)
                        .error(R.drawable.ic_car_placeholder)
                        .into(ivAdapterCarIcon)
                } catch (e: Exception) {
                    e.printStackTrace()
                    ivAdapterCarIcon.setImageResource(R.drawable.ic_car_placeholder)
                }
            }
        }
    }
}

/**
 *   differCallback checks the difference between two classes in order to prevent the recycler from
 *   blinking when notifying the list and paging as well.
 */
private val differCallback = object : DiffUtil.ItemCallback<CarItem>() {
    override fun areItemsTheSame(oldItem: CarItem, newItem: CarItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: CarItem, newItem: CarItem): Boolean =
        oldItem.id == newItem.id && oldItem.name == newItem.name
}