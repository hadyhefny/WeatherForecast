package com.example.weatherforecast.modules.recents.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.modules.recents.domain.interactor.GetRecentLocationsUseCase
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
class RecentViewModel @Inject constructor(
    private val getRecentLocationsUseCase: GetRecentLocationsUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _uiState = MutableStateFlow(emptyList<String>())
    val uiState: StateFlow<List<String>>
        get() = _uiState
    private val _uiError = MutableSharedFlow<String?>()
    val uiError: SharedFlow<String?>
        get() = _uiError

    fun getRecentLocations(count: Int) {
        getRecentLocationsUseCase.invoke(count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _uiState.value = it
            }, {})
            .addTo(compositeDisposable)
    }

//    fun getLocationForecastWeatherData(param: ForecastWeatherParam) {
//        if (param.isAllNullOrBlank()) {
//            return
//        }
//        getLocationForecastWeatherDataUseCase.invoke(param)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe {
//                updateError(null)
//                _uiState.value = _uiState.value.copy(isLoading = true)
//            }
//            .subscribe({
//                _uiState.value = it.toUiState()
//            }, {
//                _uiState.value = _uiState.value.copy(isLoading = false)
//                updateError(it.message)
//            })
//            .addTo(compositeDisposable)
//    }

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