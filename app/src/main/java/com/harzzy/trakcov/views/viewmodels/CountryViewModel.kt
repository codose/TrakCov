package com.harzzy.trakcov.views.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harzzy.trakcov.api.response.international.InternationalResponseItem
import com.harzzy.trakcov.dataStoreArchitechture.NetworkRepository
import com.harzzy.trakcov.utils.Resource
import kotlinx.coroutines.*

class CountryViewModel(context: Context) : ViewModel() {
    private val job = Job()

    private val repository = NetworkRepository(context)
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    private val _countryData = MutableLiveData<Resource<List<InternationalResponseItem>>>()

    val countrydata : LiveData<Resource<List<InternationalResponseItem>>>
        get() = _countryData

    init {
        getCountryData()
    }

    private fun getCountryData() {
        _countryData.value = Resource.Loading()
        uiScope.launch {
            withContext(Dispatchers.IO){
                _countryData.postValue(repository.getCountryCases())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}