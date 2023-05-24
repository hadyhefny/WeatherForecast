package com.example.weatherforecast.modules.forecast.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentForecastBinding
import com.example.weatherforecast.modules.forecast.domain.entity.ForecastWeatherParam
import com.example.weatherforecast.modules.forecast.presentation.view.adapter.ForecastAdapter
import com.example.weatherforecast.modules.forecast.presentation.viewmodel.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ForecastFragment : Fragment() {
    private lateinit var binding: FragmentForecastBinding
    private val viewModel by viewModels<ForecastViewModel>()
    private val TAG = "AppDebug"
    private var selectedFilter = 0

    @Inject
    lateinit var forecastAdapter: ForecastAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCollectors()
        initRecyclerView()
        initSpinner()
        initListeners()
        viewModel.getSavedLocationForecastWeatherData()
    }

    private fun initListeners() {
        binding.searchLayout.goTv.setOnClickListener {
            when (selectedFilter) {
                1 -> {
                    viewModel.getLocationForecastWeatherData(ForecastWeatherParam(cityName = binding.searchLayout.searchEt.text.toString()))
                }

                2 -> {
                    viewModel.getLocationForecastWeatherData(ForecastWeatherParam(zipCode = binding.searchLayout.searchEt.text.toString()))
                }

                3 -> {
                    viewModel.getLocationForecastWeatherData(
                        ForecastWeatherParam(
                            latitude = binding.searchLayout.searchLatEt.text.toString()
                                .toDoubleOrNull(),
                            longitude = binding.searchLayout.searchLongEt.text.toString()
                                .toDoubleOrNull()
                        )
                    )
                }

                4 -> {
                    // currentLocation
                }
            }
        }
    }

    private fun initSpinner() {
        val items = arrayOf(
            getString(R.string.select_filter),
            getString(R.string.city_name),
            getString(R.string.zip_code),
            getString(R.string.lat_long),
            getString(R.string.current_location)
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, items)
        binding.searchLayout.filterSpinner.adapter = adapter

        binding.searchLayout.filterSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    selectedFilter = position
                    Log.d(TAG, "onItemSelected:${items[position]} ")
                    if (position == 3) {
                        binding.searchLayout.searchEt.visibility = View.INVISIBLE
                        binding.searchLayout.searchLatEt.visibility = View.VISIBLE
                        binding.searchLayout.searchLongEt.visibility = View.VISIBLE
                    } else {
                        binding.searchLayout.searchEt.visibility = View.VISIBLE
                        binding.searchLayout.searchLatEt.visibility = View.INVISIBLE
                        binding.searchLayout.searchLongEt.visibility = View.INVISIBLE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Do nothing
                    Log.d(TAG, "onNothingSelected: called")
                }
            }
    }

    private fun initRecyclerView() {
        binding.rv.apply {
            adapter = forecastAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initCollectors() {
        renderState()
        renderEffects()
    }

    private fun renderState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    forecastAdapter.submitList(it.items)
                    binding.cityNameTv.text = it.name
                    binding.clLoading.isVisible = it.isLoading
                }
            }
        }
    }

    private fun renderEffects() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiError.collectLatest {
                    binding.clError.isVisible = !it.isNullOrBlank()
                    binding.errorTv.text = it
                }
            }
        }
    }
}