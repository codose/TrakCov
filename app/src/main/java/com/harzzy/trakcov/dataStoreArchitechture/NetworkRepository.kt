package com.harzzy.trakcov.dataStoreArchitechture

import android.content.Context
import com.harzzy.trakcov.api.response.country.Countrydata
import com.harzzy.trakcov.api.RetrofitClient
import com.harzzy.trakcov.api.response.continent.ContinentResponse
import com.harzzy.trakcov.api.response.continent.ContinentResponseItem
import com.harzzy.trakcov.api.response.global.GlobalResponse
import com.harzzy.trakcov.api.response.international.InternationalResponseItem
import com.harzzy.trakcov.api.response.news.Article
import com.harzzy.trakcov.api.response.news.NewsResponse
import com.harzzy.trakcov.api.response.state.State
import com.harzzy.trakcov.api.response.state.nigeria.Data
import com.harzzy.trakcov.api.response.trend.Timeline
import com.harzzy.trakcov.api.response.trend.TrendResponse
import com.harzzy.trakcov.utils.Resource
import java.lang.Exception

/* Created by : Osemwingie Oshodin (codose)
*
* Date : 27th April, 2020
*
* */


class NetworkRepository(context : Context) {

    val retrofitClient = RetrofitClient(context)

    suspend fun getNigeriaCases() : Resource<InternationalResponseItem>{
        return try {
            val getCountry = retrofitClient.covidCountryService().searchCountry("Nigeria").await()
            val cases : ArrayList<InternationalResponseItem> = ArrayList()
            cases.add(getCountry)
            Resource.Success(cases[0])
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun getGlobalCases() : Resource<GlobalResponse>{
        return try {
            val getGlobal = retrofitClient.covidCountryService().getWorldData().await()
            Resource.Success(getGlobal)
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun getStateCases() : Resource<List<Data>>{
        return try {
            val getStates = retrofitClient.covidStateService().getStatesData().await()
            val cases = getStates.data
            val ng = cases.filter {
                it.countryCode.contains("ng")
            }
            Resource.Success(ng.sortedByDescending{ it.confirmed })
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun getCountryCases() : Resource<ArrayList<InternationalResponseItem>>{
        return try {
            val getCountry = retrofitClient.covidCountryService().getAllCountries("cases").await()
            val cases : ArrayList<InternationalResponseItem> = ArrayList()
            cases.addAll(getCountry)
            Resource.Success(cases)
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun searchCountry(query: String?): Resource<List<InternationalResponseItem>> {
        return try {
            val getCountry = retrofitClient.covidCountryService().searchCountry(query!!).await()
            val cases : ArrayList<InternationalResponseItem> = ArrayList()
            cases.add(getCountry)
            Resource.Success(cases)
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun getContinentCountries(query: ArrayList<String>?): Resource<List<InternationalResponseItem>> {
        return try {
            val getCountry = retrofitClient.covidCountryService().getCountriesInContinent(query!!).await()
            val cases : ArrayList<InternationalResponseItem> = ArrayList()
            cases.addAll(getCountry)
            Resource.Success(cases)
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun getContinent(): Resource<List<ContinentResponseItem>> {
        return try {
            val getContinents = retrofitClient.covidCountryService().getContinents().await()
            val continents = ArrayList<ContinentResponseItem>()
            continents.addAll(getContinents)
            Resource.Success(continents)
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun getTimeLine(query: String?): Resource<Timeline> {
        return try {
            val getCountry = retrofitClient.covidCountryService().getTimeLine(query!!).await()
            val timeline = getCountry.timeline
            Resource.Success(timeline)
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun getGlobalTimeLine(): Resource<Timeline> {
        return try {
            val getCountry = retrofitClient.covidCountryService().getGlobalTimeLine().await()
            Resource.Success(getCountry)
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun getNewsHeadline() : Resource<List<Article>>{
        return try{
            val getNews = retrofitClient.covidNewsService().getHeadlineNews().await()
            val news = getNews.articles
            Resource.Success(news)
        } catch(e : Exception){
            Resource.Failure(e.message!!)
        }
    }
}