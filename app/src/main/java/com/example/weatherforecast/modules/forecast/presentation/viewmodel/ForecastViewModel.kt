package com.example.weatherforecast.modules.forecast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.core.domain.entity.WeatherParam
import com.example.weatherforecast.modules.forecast.domain.interactor.GetLocationForecastWeatherDataUseCase
import com.example.weatherforecast.modules.forecast.domain.interactor.GetSavedLocationForecastWeatherDataUseCase
import com.example.weatherforecast.modules.forecast.presentation.mapper.toUiState
import com.example.weatherforecast.modules.forecast.presentation.model.ForecastUiState
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
class ForecastViewModel @Inject constructor(
    private val getSavedLocationForecastWeatherDataUseCase: GetSavedLocationForecastWeatherDataUseCase,
    private val getLocationForecastWeatherDataUseCase: GetLocationForecastWeatherDataUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _uiState = MutableStateFlow(ForecastUiState())
    val uiState: StateFlow<ForecastUiState>
        get() = _uiState
    private val _uiError = MutableSharedFlow<String?>()
    val uiError: SharedFlow<String?>
        get() = _uiError

    init {
        getSavedLocationForecastWeatherData()
    }

    private fun getSavedLocationForecastWeatherData() {
        getSavedLocationForecastWeatherDataUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                updateError(null)
                _uiState.value = _uiState.value.copy(isLoading = true)
            }
            .subscribe({
                _uiState.value = it.toUiState()
            }, {
                _uiState.value = _uiState.value.copy(isLoading = false)
                updateError(it.message)
            })
            .addTo(compositeDisposable)
    }

    fun getLocationForecastWeatherData(param: WeatherParam) {
        if (param.isAllNullOrBlank()) {
            return
        }
        getLocationForecastWeatherDataUseCase.invoke(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                updateError(null)
                _uiState.value = _uiState.value.copy(isLoading = true)
            }
            .subscribe({
                updateError(null)
                _uiState.value = it.toUiState()
            }, {
                _uiState.value = _uiState.value.copy(isLoading = false)
                updateError(it.message)
            })
            .addTo(compositeDisposable)
    }

    private fun updateError(error: String?) {
        viewModelScope.launch {
            _uiError.emit(error)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}