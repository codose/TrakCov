package com.harzzy.trakcov.api

import com.harzzy.trakcov.api.Response.CountryResponse
import com.harzzy.trakcov.api.Response.Countrydata
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidService {
    @GET("free-api")
    fun getCountryData(@Query("countryTotal") country : String) : Deferred<CountryResponse>
}