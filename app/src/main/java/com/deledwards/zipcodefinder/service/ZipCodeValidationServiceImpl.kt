package com.deledwards.zipcodefinder.service

import com.deledwards.zipcodefinder.domain.ZipCodeValidationService
import javax.inject.Inject

class ZipCodeValidationServiceImpl @Inject constructor(
    private val zipLookups: Set<String>
) : ZipCodeValidationService {
    override suspend fun checkZipCodeIsValidUSZipCode(zip: String): Boolean {
        return zipLookups.contains(zip)
    }

}