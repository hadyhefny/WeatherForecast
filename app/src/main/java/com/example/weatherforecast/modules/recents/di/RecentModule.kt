package com.example.weatherforecast.modules.recents.di

import com.example.weatherforecast.core.data.source.local.LocalLocationDs
import com.example.weatherforecast.modules.recents.data.repository.RecentRepositoryImpl
import com.example.weatherforecast.modules.recents.domain.repostiory.RecentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object RecentModule {

    @ViewModelScoped
    @Provides
    fun provideRecentRepository(localLocationDs: LocalLocationDs): RecentRepository {
        return RecentRepositoryImpl(localLocationDs)
    }

}