package com.harzzy.trakcov.dataStoreArchitechture

import com.harzzy.trakcov.api.Response.Countrydata
import com.harzzy.trakcov.api.RetrofitClient
import com.harzzy.trakcov.utils.Resource
import java.lang.Exception

class NetworkRepository {

    suspend fun getCases() : Resource<Countrydata>{
        return try {
            val getCountries = RetrofitClient.ApiService.covidService().getCountryData("NG").await()
            val cases = getCountries.countrydata[0]
            Resource.Success(cases)
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }

    }
}