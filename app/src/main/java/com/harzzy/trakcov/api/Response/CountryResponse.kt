package com.harzzy.trakcov.api.Response

data class CountryResponse(
    val countrydata: List<Countrydata>,
    val stat: String
)