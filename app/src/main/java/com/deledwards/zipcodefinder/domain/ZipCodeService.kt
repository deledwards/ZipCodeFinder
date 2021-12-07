package com.deledwards.zipcodefinder.domain

import com.deledwards.zipcodefinder.domain.model.ZipCode

interface ZipCodeService {

    suspend fun getZipCodesWithRadius(zip: String,
                                      radiusInKm: Int) : List<ZipCode>


}

