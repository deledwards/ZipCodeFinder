package com.deledwards.zipcodefinder.domain

import arrow.core.Either
import com.deledwards.zipcodefinder.domain.model.ZipCode

interface ZipCodeService {

//    suspend fun getZipCodesWithRadius(zip: String,
//                                      radiusInKm: Int) : List<ZipCode>

    suspend fun getZipCodesWithRadius2(zip: String,
                                      radiusInKm: Int) : Either<Throwable, List<ZipCode>>


}

