package com.example.weatherforecast.modules.dashboard.presentation.view

import android.os.Bundle
import android.text.format.DateUtils
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
import com.example.weatherforecast.databinding.FragmentDashboardBinding
import com.example.weatherforecast.modules.dashboard.presentation.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel by viewModels<DashboardViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initCollectors()
        viewModel.getSavedLocationWeatherData()
    }

    private fun initListeners() {
        binding.retryBtn.setOnClickListener {
            viewModel.getSavedLocationWeatherData()
        }
        binding.forecastBtn.setOnClickListener {
            findNavController().navigate(R.id.forecastFragment)
        }
        binding.currentWeatherBtn.setOnClickListener {
            findNavController().navigate(R.id.currentWeatherFragment,)
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
                    binding.tempTv.text = getString(R.string.temp_celsius, it.temp.toString())
                    binding.dateTv.text =
                        DateUtils.formatDateTime(
                            context,
                            TimeUnit.SECONDS.toMillis(it.time),
                            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME,
                        )
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