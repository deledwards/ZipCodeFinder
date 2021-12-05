package com.deledwards.zipcodefinder.data.model

import com.google.gson.annotations.SerializedName

data class ZipCode (
    @SerializedName("zip_code")
    var code: String,
    var distance : String,
    var city: String,
    var state: String

)