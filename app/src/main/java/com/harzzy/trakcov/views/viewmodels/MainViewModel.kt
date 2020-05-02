package com.harzzy.trakcov.views.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harzzy.trakcov.api.response.country.Countrydata
import com.harzzy.trakcov.api.response.global.GlobalResponse
import com.harzzy.trakcov.api.response.international.InternationalResponseItem
import com.harzzy.trakcov.api.response.state.State
import com.harzzy.trakcov.api.response.state.nigeria.Data
import com.harzzy.trakcov.dataStoreArchitechture.NetworkRepository
import com.harzzy.trakcov.utils.Resource
import kotlinx.coroutines.*

/* Created by : Osemwingie Oshodin (codose)
*
* Date : 27th April, 2020
*
* */

class MainViewModel(context: Context) : ViewModel() {
    private val job = Job()

    private val repository = NetworkRepository(context)
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    private val _nigeriaData = MutableLiveData<Resource<InternationalResponseItem>>()
    private val _globalData = MutableLiveData<Resource<GlobalResponse>>()
    private val _stateData = MutableLiveData<Resource<List<Data>>>()

    val stateData : LiveData<Resource<List<Data>>>
        get() = _stateData

    val nigeriaData : LiveData<Resource<InternationalResponseItem>>
        get() = _nigeriaData

    val globalData : LiveData<Resource<GlobalResponse>>
        get() = _globalData


    init {
        getAllData()
    }


    fun getAllData(){
        _nigeriaData.value = Resource.Loading()
        _stateData.value = Resource.Loading()
        uiScope.launch {
            withContext(Dispatchers.IO){
                val ng = repository.getNigeriaCases()
                val global = repository.getGlobalCases()
                val state = repository.getStateCases()
                _stateData.postValue(state)
                _globalData.postValue(global)
                _nigeriaData.postValue(ng)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}