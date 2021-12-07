package com.deledwards.zipcodefinder.service.di

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.deledwards.zipcodefinder.BuildConfig
import com.deledwards.zipcodefinder.domain.ZipCodeService
import com.deledwards.zipcodefinder.domain.ZipCodeValidationService
import com.deledwards.zipcodefinder.service.ZipCodeAPI
import com.deledwards.zipcodefinder.service.ZipCodeServiceImpl
import com.deledwards.zipcodefinder.service.ZipCodeValidationServiceImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.util.stream.Collectors
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
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(API_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideZipCodeService (retrofit: Retrofit, validationService: ZipCodeValidationService): ZipCodeService {
        val api =  retrofit.create(ZipCodeAPI::class.java)
        return ZipCodeServiceImpl(api, validationService)
    }

    @Singleton
    @Provides
    fun provideZipCodeValidationService(@ApplicationContext appContext: Context): ZipCodeValidationService {
        val set = readFromRaw(appContext)

        return ZipCodeValidationServiceImpl(set)
    }

    private fun readFromRaw(ctx: Context): Set<String> {
        val set = mutableSetOf<String>()
        try {
            val res: Resources =  ctx.getResources()
            val id = res.getIdentifier("raw/zip_lookup_txt", null, ctx.packageName) //this.packageName )
            val inputStream: InputStream = res.openRawResource(id)

            val b = ByteArray(inputStream.available())
            inputStream.read(b)
            val s = String(b)
            val lines = s.lines()
            set.addAll(lines)
        } catch (e: java.lang.Exception) {
            e.printStackTrace();
            Log.e("App", "couldn't read raw resource (zips)")
        }
        return set
    }


}