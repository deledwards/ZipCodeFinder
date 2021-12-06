package com.deledwards.zipcodefinder.service

import com.deledwards.zipcodefinder.data.model.ZipCode
import com.deledwards.zipcodefinder.domain.ZipCodeService
import javax.inject.Inject

class ZipCodeServiceImpl @Inject constructor(
    private val api: ZipCodeAPI
) : ZipCodeService {

    override suspend fun getZipCodesWithRadius(zip: String, radiusInKm: Int): List<ZipCode> {

        val ret = api.getZipCodesWithRadius(zip, radiusInKm)
        return ret.zipCodes

    }

}