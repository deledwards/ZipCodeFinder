package com.deledwards.zipcodefinder.service

import com.deledwards.zipcodefinder.BuildConfig
import com.deledwards.zipcodefinder.data.model.ZipCodes
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface ZipCodeAPI {

    @GET("radius.json/{zip}/{radiusInKm}/km")
    suspend fun getZipCodesWithRadius(@Path(value = "zip", encoded = false) zip: String,
                                      @Path( value="radiusInKm", encoded = false ) radiusInKm: Int) : ZipCodes


    companion object {

        private val API_URL = "https://www.zipcodeapi.com/rest/${BuildConfig.REST_API}/"

        fun create() : ZipCodeAPI {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_URL)
                .build()
            return retrofit.create(ZipCodeAPI::class.java)
        }
    }
}