package com.deledwards.zipcodefinder.domain

import com.deledwards.zipcodefinder.data.model.ZipCode
import com.deledwards.zipcodefinder.data.model.ZipCodes
import retrofit2.http.Path

interface ZipCodeService {

    suspend fun getZipCodesWithRadius(zip: String,
                                      radiusInKm: Int) : List<ZipCode>


}