package com.example.weatherforecast.modules.recents.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.R
import com.example.weatherforecast.core.data.model.WeatherParam
import com.example.weatherforecast.core.domain.entity.DegreeUnit
import com.example.weatherforecast.databinding.FragmentRecentBinding
import com.example.weatherforecast.modules.forecast.presentation.view.ForecastFragment.Companion.SEARCH_PARAM
import com.example.weatherforecast.modules.recents.presentation.view.adapter.RecentAdapter
import com.example.weatherforecast.modules.recents.presentation.viewmodel.RecentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecentFragment : Fragment() {
    private lateinit var binding: FragmentRecentBinding

    @Inject
    lateinit var recentAdapter: RecentAdapter
    private val viewModel by viewModels<RecentViewModel>()
    private var selectedFilter = 0
    private val args: RecentFragmentArgs by navArgs()
    private var unit = DegreeUnit.Celsius

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        renderState()
        initSpinner()
        initListeners()
        viewModel.getRecentLocations(args.recentCount)
        binding.searchRecentLayout.unitSwitch.isVisible =
            findNavController().previousBackStackEntry?.destination?.id == R.id.currentWeatherFragment
    }

    private fun initListeners() {
        binding.searchRecentLayout.unitSwitch.setOnCheckedChangeListener { _, isChecked ->
            unit = if (isChecked) DegreeUnit.Fahrenheit else DegreeUnit.Celsius
        }
        recentAdapter.onIemCLicked = {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                SEARCH_PARAM,
                WeatherParam(cityName = it, unit = unit)
            )
            findNavController().popBackStack()
        }
        var weatherParam = WeatherParam()
        binding.searchRecentLayout.goTv.setOnClickListener {
            when (selectedFilter) {
                1 -> {
                    weatherParam =
                        WeatherParam(
                            cityName = binding.searchRecentLayout.searchEt.text.toString(),
                            unit = unit
                        )

                }

                2 -> {
                    weatherParam =
                        WeatherParam(
                            cityName = binding.searchRecentLayout.searchEt.text.toString(),
                            unit = unit
                        )
                }

                3 -> {
                    weatherParam = WeatherParam(
                        latitude = binding.searchRecentLayout.searchLatEt.text.toString()
                            .toDoubleOrNull(),
                        longitude = binding.searchRecentLayout.searchLongEt.text.toString()
                            .toDoubleOrNull(), unit = unit
                    )
                }
            }
            if (selectedFilter != 0 && !weatherParam.isAllNullOrBlank()) {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    SEARCH_PARAM,
                    weatherParam
                )
                findNavController().popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.select_filter_first),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.rv.apply {
            adapter = recentAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun renderState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    recentAdapter.submitList(it)
                    binding.savedLocationsTv.visibility =
                        if (it.isEmpty()) View.INVISIBLE else View.VISIBLE
                }
            }
        }
    }

    private fun initSpinner() {
        val items = arrayOf(
            getString(R.string.select_filter),
            getString(R.string.city_name),
            getString(R.string.zip_code),
            getString(R.string.lat_long)
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, items)
        binding.searchRecentLayout.filterSpinner.adapter = adapter

        binding.searchRecentLayout.filterSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedFilter = position
                    if (position == 3) {
                        binding.searchRecentLayout.searchEt.visibility = View.INVISIBLE
                        binding.searchRecentLayout.searchLatEt.visibility = View.VISIBLE
                        binding.searchRecentLayout.searchLongEt.visibility = View.VISIBLE
                    } else {
                        binding.searchRecentLayout.searchEt.visibility = View.VISIBLE
                        binding.searchRecentLayout.searchLatEt.visibility = View.INVISIBLE
                        binding.searchRecentLayout.searchLongEt.visibility = View.INVISIBLE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
    }
}