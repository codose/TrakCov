package com.harzzy.trakcov.views.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harzzy.trakcov.api.response.international.InternationalResponseItem
import com.harzzy.trakcov.api.response.trend.TrendResponse
import com.harzzy.trakcov.dataStoreArchitechture.NetworkRepository
import com.harzzy.trakcov.utils.Resource
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.*

class CountryViewModel(context: Context) : ViewModel() {
    private val job = Job()

    private val repository = NetworkRepository(context)
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    private val _countryData = MutableLiveData<Resource<List<InternationalResponseItem>>>()

    val countryData : LiveData<Resource<List<InternationalResponseItem>>>
        get() = _countryData

    private val _countryTrend = MutableLiveData<Resource<TrendResponse>>()

    val countryTrend : LiveData<Resource<TrendResponse>>
        get() = _countryTrend

    init {
        val query = Prefs.getString("Query","QUERY_IS_NULL")
        if(query == "QUERY_IS_NULL"){
            getCountryData()
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

    fun getTimeline(query: String?) {
        _countryTrend.value = Resource.Loading()
        uiScope.launch {
            _countryTrend.value = withContext(Dispatchers.Default){
                repository.getTimeLine(query)
            }
        }
    }
}