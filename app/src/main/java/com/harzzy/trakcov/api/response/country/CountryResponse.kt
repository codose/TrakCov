package com.harzzy.trakcov.api.response.country

import com.harzzy.trakcov.api.response.country.Countrydata

data class CountryResponse(
    val countrydata: List<Countrydata>,
    val stat: String
)