package com.deledwards.zipcodefinder.service

import arrow.core.Either
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

//    override suspend fun getZipCodesWithRadius(zip: String, radiusInKm: Int): List<ZipCode> {
//
//        if(!validationService.checkZipCodeIsValidUSZipCode(zip))
//            throw ZipCodeNotFoundException("Zip code '$zip' not found.")
//
//        val apiRet = api.getZipCodesWithRadius(zip, radiusInKm)
//
//        if(apiRet.zipCodes.isEmpty()) return listOf()
//
//        val mutableZips = mutableListOf<ApiZipCode>()
//        mutableZips.addAll(apiRet.zipCodes)
//        val zipsWithRadius = mutableZips.filter{ it.code != zip }
//
//        //adapt deserialized zipcodes to domain representations
//        return adaptZipCodes(zipsWithRadius)
//
//    }

    override suspend fun getZipCodesWithRadius2(
        zip: String,
        radiusInKm: Int
    ): Either<Throwable, List<ZipCode>> {
        try {
            if(zip.length != 5){
                return Either.Left(ZipCodeInvalidLengthException("Expected zip code of length 5, instead of ${zip.length}"))
            }

            val reg = Regex("[0-9]+")
            if(!reg.matches(zip)){
                return Either.Left(ZipCodeInvalidCharactersException("Zip code must contain only numeric characters"))
            }

            if (!validationService.checkZipCodeIsValidUSZipCode(zip)) {
                return Either.Left(ZipCodeNotFoundException("Zip code '$zip' not found."))
            }

            val apiRet = api.getZipCodesWithRadius(zip, radiusInKm)

            if (apiRet.zipCodes.isEmpty()) return Either.Left(
                NoResultsWithinRadiusException("No results found within $radiusInKm km of '$zip' ")
            )

            val mutableZips = mutableListOf<ApiZipCode>()
            mutableZips.addAll(apiRet.zipCodes)
            val zipsWithRadius = mutableZips.filter { it.code != zip }

            //adapt deserialized zipcodes to domain representations
            val ret = adaptZipCodes(zipsWithRadius)

            return Either.Right(ret)
        }catch (ex: Exception){
            return Either.Left(ex)
        }
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

class ZipCodeInvalidLengthException(message: String) : Exception(message)

class ZipCodeInvalidCharactersException(message: String) : Exception(message)

class NoResultsWithinRadiusException(message: String) : Exception(message)


