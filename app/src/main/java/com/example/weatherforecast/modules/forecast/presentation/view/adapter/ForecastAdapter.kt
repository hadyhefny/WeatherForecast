package com.example.weatherforecast.modules.forecast.presentation.view.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.ItemForecastBinding
import com.example.weatherforecast.modules.forecast.presentation.model.ForecastItemUiState
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ForecastAdapter @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<ForecastItemUiState> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ForecastViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(list: List<ForecastItemUiState>) {
        items = list
        notifyDataSetChanged()
    }

    class ForecastViewHolder constructor(
        private val binding: ItemForecastBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ForecastItemUiState) = with(binding) {
            descriptionTv.text = item.description
            tempTv.text =
                binding.root.context.getString(R.string.temp_celsius, item.temp.toString())
            dateTv.text = DateUtils.formatDateTime(
                binding.root.context,
                TimeUnit.SECONDS.toMillis(item.time),
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME
            )
        }
    }
}