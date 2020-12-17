package com.example.carpetcleaner.ui.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carpetcleaner.data.ScheduledService
import com.example.carpetcleaner.databinding.ScheduleServiceListItemBinding

class ScheduledServiceAdapter(private val clickListener: ScheduledServiceListener): ListAdapter<ScheduledService, ScheduledServiceAdapter.ViewHolder>(
    ScheduledServiceDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    //used to inflate the view for each data item
    class ViewHolder private constructor(val binding: ScheduleServiceListItemBinding): RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ScheduleServiceListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: ScheduledService, clickListener: ScheduledServiceListener){
            val res = itemView.context.resources
            binding.scheduledService = item
            binding.clickListener = clickListener
            val subServiceAdapter = SubServiceAdapter()
            binding.additionalInfo.adapter = subServiceAdapter
            subServiceAdapter.submitList(item.subServices)
            binding.expandableLayout.visibility = item.getExpanded()
        }
    }
}

class ScheduledServiceDiffCallback: DiffUtil.ItemCallback<ScheduledService>(){
    override fun areItemsTheSame(oldItem: ScheduledService, newItem: ScheduledService): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ScheduledService, newItem: ScheduledService): Boolean = oldItem == newItem
}

//interface for recyclerview list interaction
interface ScheduledServiceListener {
    fun onDeleteClicked(scheduledService: ScheduledService)

    fun onEditClicked(scheduledService: ScheduledService)

    fun onPopUpClicked(view: View, scheduledService: ScheduledService)

    fun onClick(view: View, scheduledService: ScheduledService)
}
