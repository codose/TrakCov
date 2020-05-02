package com.harzzy.trakcov.views.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harzzy.trakcov.api.response.continent.ContinentResponse
import com.harzzy.trakcov.api.response.continent.ContinentResponseItem
import com.harzzy.trakcov.api.response.global.GlobalResponse
import com.harzzy.trakcov.api.response.international.InternationalResponseItem
import com.harzzy.trakcov.api.response.trend.Timeline
import com.harzzy.trakcov.api.response.trend.TrendResponse
import com.harzzy.trakcov.dataStoreArchitechture.NetworkRepository
import com.harzzy.trakcov.utils.Resource
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.*

/* Created by : Osemwingie Oshodin (codose)
*
* Date : 27th April, 2020
*
* */

class CountryViewModel(context: Context) : ViewModel() {

    private val job = Job()

    private val repository = NetworkRepository(context)
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    private val _countryData = MutableLiveData<Resource<List<InternationalResponseItem>>>()

    val countryData : LiveData<Resource<List<InternationalResponseItem>>>
        get() = _countryData

    private val _countryTrend = MutableLiveData<Resource<Timeline>>()

    val countryTrend : LiveData<Resource<Timeline>>
        get() = _countryTrend

    private val _continentData = MutableLiveData<Resource<List<ContinentResponseItem>>>()

    val continentData : LiveData<Resource<List<ContinentResponseItem>>>
        get() = _continentData

    private val _globalData = MutableLiveData<Resource<GlobalResponse>>()

    val globalData : LiveData<Resource<GlobalResponse>>
        get() = _globalData


    init {
        val query = Prefs.getString("Query","QUERY_IS_NULL")
        if(query == "QUERY_IS_NULL"){
            getContinents()
        }
    }

    fun getCountryData() {
        _countryData.value = Resource.Loading()
        uiScope.launch {
            withContext(Dispatchers.Default){
                _countryData.postValue(repository.getCountryCases())
            }
        }
    }

    fun getGlobalData(){
        _globalData.value = Resource.Loading()
        uiScope.launch {
            withContext(Dispatchers.IO){
                val global = repository.getGlobalCases()
                _globalData.postValue(global)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun searchCountry(query: String?) {
        _countryData.value = Resource.Loading()
        uiScope.launch {
            withContext(Dispatchers.Default){
                _countryData.postValue(repository.searchCountry(query))
            }
        }
    }

    fun getContinents(){
        _continentData.value = Resource.Loading()
        uiScope.launch {
            _continentData.value = withContext(Dispatchers.IO){
                repository.getContinent()
            }
        }
    }

    fun getContinentCountries(query: ArrayList<String>) {
        _countryData.value = Resource.Loading()
        uiScope.launch {
            withContext(Dispatchers.Default){
                _countryData.postValue(repository.getContinentCountries(query))
            }
        }
    }

    fun getTimeline(query: String?) {
        _countryTrend.value = Resource.Loading()
        uiScope.launch {
            _countryTrend.value = withContext(Dispatchers.Default){
                repository.getTimeLine(query)
            }
        }
    }

    fun getGlobalTimeline() {
        _countryTrend.value = Resource.Loading()
        uiScope.launch {
            _countryTrend.value = withContext(Dispatchers.Default){
                repository.getGlobalTimeLine()
            }
        }
    }

}