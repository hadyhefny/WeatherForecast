package com.example.weatherforecast.modules.recents.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.databinding.ItemRecentBinding
import javax.inject.Inject

class RecentAdapter @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<String> = emptyList()
    var onIemCLicked: ((String)->Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemRecentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecentViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(list: List<String>) {
        items = list
        notifyDataSetChanged()
    }

    inner class RecentViewHolder constructor(
        private val binding: ItemRecentBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) = with(binding) {
            recentTv.text = item
            binding.root.setOnClickListener {
                onIemCLicked?.invoke(item)
            }
        }
    }
}