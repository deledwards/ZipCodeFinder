package com.deledwards.zipcodefinder.domain.model

data class ZipCode (
    var code: String,
    var distance : Float,
    var city: String,
    var state: State
    )