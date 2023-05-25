package com.example.weatherforecast.modules.recents.domain.repostiory

import io.reactivex.rxjava3.core.Single

interface RecentRepository {
    fun getRecentLocations(): Single<List<String>>
}