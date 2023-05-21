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
    private val TAG = "AppDebug"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSavedLocationWeatherData()
        initObservers()
    }

    private fun initObservers() {
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
                            DateUtils.FORMAT_SHOW_DATE
                        )
                    binding.clLoading.isVisible = it.isLoading
                }
                viewModel.uiError.collectLatest {
                    binding.clError.isVisible = !it.isNullOrBlank()
                    binding.errorTv.text = it
                }
            }
        }
    }
}