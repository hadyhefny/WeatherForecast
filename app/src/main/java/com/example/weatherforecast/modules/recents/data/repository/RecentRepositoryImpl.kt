package com.example.weatherforecast.modules.recents.data.repository

import com.example.weatherforecast.core.data.source.local.LocalLocationDs
import com.example.weatherforecast.modules.recents.domain.repostiory.RecentRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RecentRepositoryImpl @Inject constructor(
    private val localLocationDs: LocalLocationDs
) : RecentRepository {

    override fun getRecentLocations(): Single<List<String>> {
        return localLocationDs.getLastSavedLocations()
    }
}