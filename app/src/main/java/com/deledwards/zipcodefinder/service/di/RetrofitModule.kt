package com.deledwards.zipcodefinder.service.di

import com.deledwards.zipcodefinder.BuildConfig
import com.deledwards.zipcodefinder.domain.ZipCodeService
import com.deledwards.zipcodefinder.service.ZipCodeAPI
import com.deledwards.zipcodefinder.service.ZipCodeServiceImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private val API_URL = "https://www.zipcodeapi.com/rest/${BuildConfig.REST_API}/"
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit (gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideZipCodeService (retrofit: Retrofit): ZipCodeService {
        val api =  retrofit.create(ZipCodeAPI::class.java)
        return ZipCodeServiceImpl(api)
    }
}