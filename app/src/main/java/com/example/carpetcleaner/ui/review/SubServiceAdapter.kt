package com.example.carpetcleaner.ui.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carpetcleaner.data.SubService
import com.example.carpetcleaner.databinding.SubserviceListItemBinding

class SubServiceAdapter: ListAdapter<SubService, SubServiceAdapter.ViewHolder>(
    SubServiceDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: SubserviceListItemBinding): RecyclerView.ViewHolder(binding.root){

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SubserviceListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: SubService){
            binding.subService = item
        }
    }
}

class SubServiceDiffCallback: DiffUtil.ItemCallback<SubService>(){
    override fun areItemsTheSame(oldItem: SubService, newItem: SubService): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: SubService, newItem: SubService): Boolean {
        return oldItem == newItem
    }

}