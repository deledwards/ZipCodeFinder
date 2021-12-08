package com.deledwards.zipcodefinder

import arrow.core.Either
import com.deledwards.zipcodefinder.data.model.ZipCodes
import com.deledwards.zipcodefinder.domain.ZipCodeService
import com.deledwards.zipcodefinder.service.*
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class ZipCodeServiceImplTest {


    @Test
    fun test_bad_zipcode_last4_unsupported_by_api(){
        //30022-8675 is a valid zip code according to UPS tracking services,
        // but unsupported by our API

        val stubValidationService = ZipCodeValidationServiceImpl(setOf("30022"))
        val api =  MockRestApi(getSampleResponse_30022_20km)
        val zipCodeService : ZipCodeService = ZipCodeServiceImpl(api, stubValidationService)
        runBlocking {
            val ret = zipCodeService.getZipCodesWithRadius2("30022-8675", 20)
            assertTrue(ret.isLeft())
            when (ret) {
                is Either.Left ->  {
                    assertTrue(ret.value is ZipCodeInvalidLengthException)
                    println(ret.value.javaClass)

                }
                is Either.Right -> {
                    fail("Should have worked")
                }
            }
        }
    }

    @Test
    fun test_bad_zipcode(){
        val stubValidationService = ZipCodeValidationServiceImpl(setOf("30022"))
        //we're not mocking 400 errors, but if this actually got returned it would blow up
        // the json serialization and fail the test.
        // the point of the test is to test validation and return type
        val api =  MockRestApi(getSampleBadRequest)
        val zipCodeService : ZipCodeService = ZipCodeServiceImpl(api, stubValidationService)
        runBlocking {
            val ret = zipCodeService.getZipCodesWithRadius2("abcde", 20)
            assertTrue(ret.isLeft())
            when (ret) {
                is Either.Left ->  {
                    println(ret.value.javaClass)
                    assertTrue("exception should be of type ZipCodeInvalidCharactersException" ,
                        ret.value is ZipCodeInvalidCharactersException)
                }
                is Either.Right -> {
                    fail("Should have worked")
                }
            }
        }
    }


    @Test
    fun test_handle_zero_radius_zipcode(){
        val stubValidationService = ZipCodeValidationServiceImpl(setOf("30022"))
        val api =  MockRestApi(getSampleRespons_zero_radius)
        val zipCodeService : ZipCodeService = ZipCodeServiceImpl(api, stubValidationService)
        runBlocking {
            val ret = zipCodeService.getZipCodesWithRadius2("abcde", 20)
            assertTrue(ret.isLeft())
            when (ret) {
                is Either.Left ->  {
                    println(ret.value.javaClass)
                    assertTrue("exception should be of type" ,ret.value is ZipCodeInvalidCharactersException)
                }
                is Either.Right -> {
                    fail("Should have worked")
                }
            }
        }
    }


    @Test
    fun get_30022_20km() {

        val stubValidationService = ZipCodeValidationServiceImpl(setOf("30022"))
        val api =  MockRestApi(getSampleResponse_30022_20km)
        val zipCodeService : ZipCodeService = ZipCodeServiceImpl(api, stubValidationService)
        runBlocking {
            val ret = zipCodeService.getZipCodesWithRadius2("30022", 20)
            assertTrue(ret.isRight())
            when (ret) {
                is Either.Left ->  {
                    //assertTrue(ret.value is Throwable)
                    fail("Should have worked")
                }
                is Either.Right -> {
                    assertTrue(ret.value.isNotEmpty())
                    val findSourceZip = ret.value.filter {
                        it.code == "30022"
                    }
                    assertTrue(findSourceZip.isEmpty())
                    //36 returned minus the input zip
                    assert(ret.value.size == 35)
                }
            }
        }
    }
}

class MockRestApi(val jsonString: String ) : ZipCodeAPI {

    override suspend fun getZipCodesWithRadius(zip: String, radiusInKm: Int): ZipCodes {
        val gson = Gson()
        return gson.fromJson(jsonString, ZipCodes::class.java)
    }

}


const val getSampleBadRequest = """
{
    "error_code": 400,
    "error_msg": "Invalid request."
}
"""

const val getSampleRespons_zero_radius = """
{
    "zip_codes": []
}
"""

const val getSampleResponse_30022_20km = """
{
    "zip_codes": [
        {
            "zip_code": "30084",
            "distance": 19.666,
            "city": "Tucker",
            "state": "GA"
        },
        {
            "zip_code": "30319",
            "distance": 19.23,
            "city": "Atlanta",
            "state": "GA"
        },
        {
            "zip_code": "30366",
            "distance": 17.297,
            "city": "Atlanta",
            "state": "GA"
        },
        {
            "zip_code": "30048",
            "distance": 19.479,
            "city": "Lilburn",
            "state": "GA"
        },
        {
            "zip_code": "30341",
            "distance": 16.376,
            "city": "Atlanta",
            "state": "GA"
        },
        {
            "zip_code": "30340",
            "distance": 14.723,
            "city": "Atlanta",
            "state": "GA"
        },
        {
            "zip_code": "30093",
            "distance": 14.666,
            "city": "Norcross",
            "state": "GA"
        },
        {
            "zip_code": "30346",
            "distance": 14.88,
            "city": "Atlanta",
            "state": "GA"
        },
        {
            "zip_code": "30328",
            "distance": 17.281,
            "city": "Atlanta",
            "state": "GA"
        },
        {
            "zip_code": "30360",
            "distance": 11.094,
            "city": "Atlanta",
            "state": "GA"
        },
        {
            "zip_code": "30356",
            "distance": 13.018,
            "city": "Atlanta",
            "state": "GA"
        },
        {
            "zip_code": "30003",
            "distance": 10.634,
            "city": "Norcross",
            "state": "GA"
        },
        {
            "zip_code": "30010",
            "distance": 10.634,
            "city": "Norcross",
            "state": "GA"
        },
        {
            "zip_code": "30091",
            "distance": 10.634,
            "city": "Norcross",
            "state": "GA"
        },
        {
            "zip_code": "30071",
            "distance": 10.382,
            "city": "Norcross",
            "state": "GA"
        },
        {
            "zip_code": "30338",
            "distance": 11.785,
            "city": "Atlanta",
            "state": "GA"
        },
        {
            "zip_code": "30068",
            "distance": 18.98,
            "city": "Marietta",
            "state": "GA"
        },
        {
            "zip_code": "30092",
            "distance": 6.427,
            "city": "Norcross",
            "state": "GA"
        },
        {
            "zip_code": "30096",
            "distance": 10.683,
            "city": "Duluth",
            "state": "GA"
        },
        {
            "zip_code": "30350",
            "distance": 10.004,
            "city": "Atlanta",
            "state": "GA"
        },
        {
            "zip_code": "30026",
            "distance": 8.931,
            "city": "North Metro",
            "state": "GA"
        },
        {
            "zip_code": "30029",
            "distance": 8.931,
            "city": "North Metro",
            "state": "GA"
        },
        {
            "zip_code": "30095",
            "distance": 8.931,
            "city": "Duluth",
            "state": "GA"
        },
        {
            "zip_code": "30098",
            "distance": 8.931,
            "city": "Duluth",
            "state": "GA"
        },
        {
            "zip_code": "30099",
            "distance": 8.931,
            "city": "Duluth",
            "state": "GA"
        },
        {
            "zip_code": "30097",
            "distance": 8.67,
            "city": "Duluth",
            "state": "GA"
        },
        {
            "zip_code": "30076",
            "distance": 6.602,
            "city": "Roswell",
            "state": "GA"
        },
        {
            "zip_code": "30022",
            "distance": 0,
            "city": "Alpharetta",
            "state": "GA"
        },
        {
            "zip_code": "30077",
            "distance": 10.19,
            "city": "Roswell",
            "state": "GA"
        },
        {
            "zip_code": "30075",
            "distance": 14.164,
            "city": "Roswell",
            "state": "GA"
        },
        {
            "zip_code": "30023",
            "distance": 4.366,
            "city": "Alpharetta",
            "state": "GA"
        },
        {
            "zip_code": "30024",
            "distance": 14.211,
            "city": "Suwanee",
            "state": "GA"
        },
        {
            "zip_code": "30009",
            "distance": 8.134,
            "city": "Alpharetta",
            "state": "GA"
        },
        {
            "zip_code": "30005",
            "distance": 6.896,
            "city": "Alpharetta",
            "state": "GA"
        },
        {
            "zip_code": "30169",
            "distance": 13.44,
            "city": "Canton",
            "state": "GA"
        },
        {
            "zip_code": "30004",
            "distance": 13.736,
            "city": "Alpharetta",
            "state": "GA"
        }
    ]
}
"""