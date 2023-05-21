package com.example.weatherforecast.modules.dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.core.domain.interactor.GetSavedLocationWeatherDataUseCase
import com.example.weatherforecast.modules.dashboard.presentation.mapper.toUiState
import com.example.weatherforecast.modules.dashboard.presentation.model.DashboardUiState
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
class DashboardViewModel @Inject constructor(
    private val getSavedLocationWeatherDataUseCase: GetSavedLocationWeatherDataUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState>
        get() = _uiState

    private val _uiError = MutableSharedFlow<Throwable>()
    val uiError: SharedFlow<Throwable>
        get() = _uiError

    fun getSavedLocationWeatherData() {
        getSavedLocationWeatherDataUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _uiState.value = it.toUiState()
            }, {
                updateError(it)
            })
            .addTo(compositeDisposable)
    }

    private fun updateError(throwable: Throwable) {
        viewModelScope.launch {
            _uiError.emit(throwable)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}