package com.deledwards.zipcodefinder.service

import com.deledwards.zipcodefinder.data.model.ZipCode as ApiZipCode
import com.deledwards.zipcodefinder.domain.ZipCodeService
import com.deledwards.zipcodefinder.domain.ZipCodeValidationService
import com.deledwards.zipcodefinder.domain.model.ZipCode
import com.deledwards.zipcodefinder.domain.model.State
import javax.inject.Inject

class ZipCodeServiceImpl @Inject constructor(
    private val api: ZipCodeAPI,
    private val validationService: ZipCodeValidationService
) : ZipCodeService {

    override suspend fun getZipCodesWithRadius(zip: String, radiusInKm: Int): List<ZipCode> {

        if(!validationService.checkZipCodeIsValid(zip))
            throw ZipCodeNotFoundException("Zip code '$zip' not found.")

        val apiRet = api.getZipCodesWithRadius(zip, radiusInKm)

        if(apiRet.zipCodes.isEmpty()) return listOf()

        val mutableZips = mutableListOf<ApiZipCode>()
        mutableZips.addAll(apiRet.zipCodes)
        val zipsWithRadius = mutableZips.filter{ it.code != zip }

        //adapt deserialized zipcodes to domain representations
        return adaptZipCodes(zipsWithRadius)

    }

    private fun adaptZipCodes(zipCodes: List<ApiZipCode>): List<ZipCode> {

        val domainList = zipCodes.map {
            val zipCode = ZipCode(
                it.code,
                it.distance.toFloat(),
                it.city,
                State.valueOfAbbreviation(it.state)
            )
            zipCode
        }
        return domainList

    }

}

class ZipCodeNotFoundException(message: String) : Exception(message)

class RadiusDistanceNotSpecifiedException(message: String) : Exception(message)

