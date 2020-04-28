package com.harzzy.trakcov.api

import com.harzzy.trakcov.api.response.country.CountryResponse
import com.harzzy.trakcov.api.response.global.GlobalResponse
import com.harzzy.trakcov.api.response.international.InternationalResponse
import com.harzzy.trakcov.api.response.state.StateResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidService {
    @GET("free-api")
    fun getCountryData(@Query("countryTotal") country : String) : Deferred<CountryResponse>

    @GET("free-api")
    fun getWorldData(@Query("global") value: String) : Deferred<GlobalResponse>

    @GET("api")
    fun getStatesData() : Deferred<StateResponse>

    @GET("countries")
    fun getAllCountries(@Query("sort" )value : String) : Deferred<InternationalResponse>

}