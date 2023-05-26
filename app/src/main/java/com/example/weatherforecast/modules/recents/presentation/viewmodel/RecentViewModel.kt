package com.example.weatherforecast.modules.recents.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherforecast.modules.recents.domain.interactor.GetRecentLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RecentViewModel @Inject constructor(
    private val getRecentLocationsUseCase: GetRecentLocationsUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _uiState = MutableStateFlow(emptyList<String>())
    val uiState: StateFlow<List<String>>
        get() = _uiState

    fun getRecentLocations(count: Int) {
        getRecentLocationsUseCase.invoke(count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _uiState.value = it
            }, {})
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}