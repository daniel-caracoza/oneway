package com.example.carpetcleaner.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.carpetcleaner.data.Service
import androidx.recyclerview.widget.ListAdapter
import com.example.carpetcleaner.databinding.ServicesListItemBinding

class ServicesAdapter(val clickListener: ServiceListener): ListAdapter<Service, ServicesAdapter.ViewHolder>(ServicesDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }


    class ViewHolder private constructor(val binding: ServicesListItemBinding): RecyclerView.ViewHolder(binding.root){

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ServicesListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: Service, clickListener: ServiceListener) {
            val res = itemView.context.resources
            binding.service = item
            binding.img.setImageResource(item.img)
            binding.title.text = item.title
            binding.clickListener = clickListener
        }
    }
}

class ServicesDiffCallback: DiffUtil.ItemCallback<Service>(){
    override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean = oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean = (oldItem == newItem)
}

interface ServiceListener {
    fun onClick(cardView: View, service: Service)
}