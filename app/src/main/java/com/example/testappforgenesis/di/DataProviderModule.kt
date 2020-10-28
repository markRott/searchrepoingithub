package com.example.testappforgenesis.di

import com.example.testappforgenesis.api.ApplicationApi
import com.example.testappforgenesis.data.DataProvider
import com.example.testappforgenesis.data.DataProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataProviderModule {

    @Singleton
    @Provides
    fun provideRepoDataProvider(api: ApplicationApi): DataProvider {
        return DataProviderImpl(api)
    }
}