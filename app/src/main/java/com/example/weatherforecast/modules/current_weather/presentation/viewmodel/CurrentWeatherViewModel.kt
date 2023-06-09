package com.example.weatherforecast.modules.current_weather.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.core.data.mapper.toEntity
import com.example.weatherforecast.core.domain.entity.DegreeUnit
import com.example.weatherforecast.core.data.model.WeatherParam
import com.example.weatherforecast.core.domain.exception.NoLocationSavedException
import com.example.weatherforecast.core.domain.interactor.GetSavedLocationWeatherDataUseCase
import com.example.weatherforecast.modules.current_weather.domain.interactor.GetCurrentLocationWeatherDataUseCase
import com.example.weatherforecast.modules.current_weather.presentation.mapper.toUiState
import com.example.weatherforecast.modules.current_weather.presentation.model.CurrentWeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getSavedLocationWeatherDataUseCase: GetSavedLocationWeatherDataUseCase,
    private val getCurrentLocationWeatherDataUseCase: GetCurrentLocationWeatherDataUseCase,
    private val application: Application
) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    private val _uiState = MutableStateFlow(CurrentWeatherUiState())
    val uiState: StateFlow<CurrentWeatherUiState>
        get() = _uiState

    private val _uiError = MutableSharedFlow<String?>(1)
    val uiError: SharedFlow<String?>
        get() = _uiError

    init {
        getSavedLocationWeatherData()
    }


    fun getSavedLocationWeatherData() {
        getSavedLocationWeatherDataUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                updateError(null)
                _uiState.value = _uiState.value.copy(isLoading = true)
            }
            .subscribe({
                _uiState.value = it.toUiState(application, DegreeUnit.Celsius)
            }, {
                _uiState.value = _uiState.value.copy(isLoading = false, isRetryButtonVisible = it !is NoLocationSavedException)
                updateError(it.message)
            })
            .addTo(compositeDisposable)
    }

    private fun updateError(error: String?) {
        viewModelScope.launch {
            _uiError.emit(error)
        }
    }

    fun getCurrentLocationWeatherData(param: WeatherParam) {
        getCurrentLocationWeatherDataUseCase.invoke(param.toEntity()).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                updateError(null)
                _uiState.value = _uiState.value.copy(isLoading = true)
            }
            .subscribe({
                _uiState.value = it.toUiState(application, param.unit)
            }, {
                _uiState.value = _uiState.value.copy(isLoading = false)
                updateError(it.message)
            })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}