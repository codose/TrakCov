package com.harzzy.trakcov.dataStoreArchitechture

import android.content.Context
import com.harzzy.trakcov.api.response.country.Countrydata
import com.harzzy.trakcov.api.RetrofitClient
import com.harzzy.trakcov.api.response.global.Result
import com.harzzy.trakcov.api.response.international.InternationalResponseItem
import com.harzzy.trakcov.api.response.state.State
import com.harzzy.trakcov.utils.Resource
import java.lang.Exception

class NetworkRepository(context : Context) {

    val retrofitClient = RetrofitClient(context)

    suspend fun getNigeriaCases(code : String) : Resource<Countrydata>{
        return try {
            val getCountries = retrofitClient.covidService().getCountryData(code).await()
            val cases = getCountries.countrydata[0]
            Resource.Success(cases)
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun getGlobalCases() : Resource<Result>{
        return try {
            val getGlobal = retrofitClient.covidService().getWorldData("stats").await()
            val cases = getGlobal.results[0]
            Resource.Success(cases)
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun getStateCases() : Resource<List<State>>{
        return try {
            val getStates = retrofitClient.covidStateService().getStatesData().await()
            val cases = getStates.data.states
            Resource.Success(cases)
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
}