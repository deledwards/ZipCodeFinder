package com.deledwards.zipcodefinder.service

import com.deledwards.zipcodefinder.data.model.ZipCode
import com.deledwards.zipcodefinder.domain.ZipCodeService
import com.deledwards.zipcodefinder.domain.ZipCodeValidationService
import javax.inject.Inject

class ZipCodeServiceImpl @Inject constructor(
    private val api: ZipCodeAPI,
    private val validationService: ZipCodeValidationService
) : ZipCodeService {

    override suspend fun getZipCodesWithRadius(zip: String, radiusInKm: Int): List<ZipCode> {
        val b = validationService.checkZipCodeIsValid(zip)

        if(!b) throw ZipCodeNotFoundException("Zip code '$zip' not found.")

        val ret = api.getZipCodesWithRadius(zip, radiusInKm)
        return ret.zipCodes

    }

}

class ZipCodeNotFoundException(message: String) : Exception(message)

class RadiusDistanceNotSpecifiedException(message: String) : Exception(message)

