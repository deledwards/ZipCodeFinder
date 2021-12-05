package com.deledwards.zipcodefinder.data.model

import com.google.gson.annotations.SerializedName

data class ZipCodes(
    @SerializedName("zip_codes")
    var zipCodes : List<ZipCode>
)


