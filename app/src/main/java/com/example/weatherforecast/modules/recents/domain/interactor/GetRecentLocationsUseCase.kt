package com.example.weatherforecast.modules.recents.domain.interactor

import com.example.weatherforecast.modules.recents.domain.repostiory.RecentRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetRecentLocationsUseCase @Inject constructor(
    private val repository: RecentRepository
) {
    operator fun invoke(param: Int): Single<List<String>> {
        return repository.getRecentLocations().map {
            it.takeLast(param).reversed()
        }
    }
}