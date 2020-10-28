package com.example.testappforgenesis.di

import com.example.testappforgenesis.BuildConfig
import com.example.testappforgenesis.api.ApplicationApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApplicationApi(): ApplicationApi {
        val retrofit = getRetrofit()
        return retrofit.create(ApplicationApi::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
                .Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient()
                .newBuilder()
                .addInterceptor(logging)
                .build()
    }

    private fun getGson() :Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }
}