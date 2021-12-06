package com.deledwards.zipcodefinder.service

import com.deledwards.zipcodefinder.data.model.ZipCodes
import retrofit2.http.GET
import retrofit2.http.Path


interface ZipCodeAPI {

    @GET("radius.json/{zip}/{radiusInKm}/km")
    suspend fun getZipCodesWithRadius(@Path(value = "zip", encoded = false) zip: String,
                                      @Path( value="radiusInKm", encoded = false ) radiusInKm: Int) : ZipCodes

}


