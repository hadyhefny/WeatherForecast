package com.example.weatherforecast.modules.forecast.presentation.view

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.core.domain.entity.WeatherParam
import com.example.weatherforecast.databinding.FragmentForecastBinding
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
        initListeners()
        viewModel.getSavedLocationForecastWeatherData()
    }

    private fun initListeners() {
        binding.searchTv.setOnClickListener {
            val action = ForecastFragmentDirections.actionForecastFragmentToRecentFragment(10)
            findNavController().navigate(action)
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                findNavController().currentBackStackEntry?.savedStateHandle?.getStateFlow<WeatherParam?>(
                    SEARCH_PARAM,
                    null
                )
                    ?.collectLatest {
                        it?.let {
                            viewModel.getLocationForecastWeatherData(it)
                        }
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

    companion object {
        const val SEARCH_PARAM = "search param"
    }
}