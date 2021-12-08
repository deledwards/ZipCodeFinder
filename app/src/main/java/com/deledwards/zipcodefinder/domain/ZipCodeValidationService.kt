package com.deledwards.zipcodefinder.domain

interface ZipCodeValidationService{

    suspend fun checkZipCodeIsValidUSZipCode(zip: String): Boolean
}