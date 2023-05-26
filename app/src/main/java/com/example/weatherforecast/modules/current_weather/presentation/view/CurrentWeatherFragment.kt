package com.example.weatherforecast.modules.current_weather.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.weatherforecast.R
import com.example.weatherforecast.core.data.model.WeatherParam
import com.example.weatherforecast.databinding.FragmentCurrentWeatherBinding
import com.example.weatherforecast.modules.current_weather.presentation.viewmodel.CurrentWeatherViewModel
import com.example.weatherforecast.modules.forecast.presentation.view.ForecastFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {
    private lateinit var binding: FragmentCurrentWeatherBinding
    private val viewModel by viewModels<CurrentWeatherViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initCollectors()
    }

    private fun initListeners() {
        binding.retryBtn.setOnClickListener {
            viewModel.getSavedLocationWeatherData()
        }
        binding.forecastBtn.setOnClickListener {
            findNavController().navigate(R.id.forecastFragment)
        }
        binding.searchTv.setOnClickListener {
            val action =
                CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToRecentFragment(5)
            findNavController().navigate(action)
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
                    binding.cityNameTv.text = it.cityName
                    binding.descriptionTv.text = it.description
                    binding.tempTv.text = it.temp
                    binding.dateTv.text = it.time
                    binding.clLoading.isVisible = it.isLoading
                    binding.retryBtn.isVisible = it.isRetryButtonVisible
                }
            }
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.get<WeatherParam>(
            ForecastFragment.SEARCH_PARAM
        )?.let {
            findNavController().currentBackStackEntry?.savedStateHandle?.remove<WeatherParam>(
                ForecastFragment.SEARCH_PARAM
            )?.let {
                viewModel.getCurrentLocationWeatherData(it)
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