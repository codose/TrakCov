package com.harzzy.trakcov.api

import com.harzzy.trakcov.api.response.continent.ContinentResponse
import com.harzzy.trakcov.api.response.country.CountryResponse
import com.harzzy.trakcov.api.response.global.GlobalResponse
import com.harzzy.trakcov.api.response.international.InternationalResponse
import com.harzzy.trakcov.api.response.international.InternationalResponseItem
import com.harzzy.trakcov.api.response.news.NewsResponse
import com.harzzy.trakcov.api.response.state.StateResponse
import com.harzzy.trakcov.api.response.state.nigeria.NigeriaResponse
import com.harzzy.trakcov.api.response.trend.Timeline
import com.harzzy.trakcov.api.response.trend.TrendResponse
import com.harzzy.trakcov.utils.Constants.NEWS_API_KEY
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CovidService {

    @GET("all")
    fun getWorldData() : Deferred<GlobalResponse>

    @GET("cities")
    fun getStatesData() : Deferred<NigeriaResponse>

    @GET("countries")
    fun getAllCountries(@Query("sort" )value : String) : Deferred<InternationalResponse>

    @GET("countries/{query}")
    fun searchCountry(@Path("query") value: String, @Query("strict") vale: String = "false") : Deferred<InternationalResponseItem>

    @GET("historical/{query}")
    fun getTimeLine(@Path("query") query: String) : Deferred<TrendResponse>

    @GET("continents")
    fun getContinents(@Query("sort" )sort : String = "cases") : Deferred<ContinentResponse>

    @GET("countries/{query}")
    fun getCountriesInContinent(@Path("query") query: ArrayList<String>, @Query("strict") vale: String = "false") : Deferred<InternationalResponse>

    @GET("top-headlines")
    fun getHeadlineNews(@Query("country" )country : String = "ng" , @Query("category") category : String = "health", @Header("Authorization") authorization : String = NEWS_API_KEY) : Deferred<NewsResponse>

    @GET("historical/all")
    fun getGlobalTimeLine() : Deferred<Timeline>
}