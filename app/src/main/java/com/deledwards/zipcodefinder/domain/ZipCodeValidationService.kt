package com.deledwards.zipcodefinder.domain

interface ZipCodeValidationService{

    suspend fun checkZipCodeIsValid(zip: String): Boolean
}