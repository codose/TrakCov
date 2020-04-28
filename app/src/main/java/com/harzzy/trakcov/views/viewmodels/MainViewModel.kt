package com.harzzy.trakcov.views.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harzzy.trakcov.api.response.country.Countrydata
import com.harzzy.trakcov.api.response.global.Result
import com.harzzy.trakcov.api.response.state.State
import com.harzzy.trakcov.dataStoreArchitechture.NetworkRepository
import com.harzzy.trakcov.utils.Resource
import kotlinx.coroutines.*

class MainViewModel(context: Context) : ViewModel() {
    private val job = Job()

    private val repository = NetworkRepository(context)
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    private val _countryData = MutableLiveData<Resource<Countrydata>>()
    private val _globalData = MutableLiveData<Resource<Result>>()
    private val _stateData = MutableLiveData<Resource<List<State>>>()

    val stateData : LiveData<Resource<List<State>>>
        get() = _stateData

    val countryData : LiveData<Resource<Countrydata>>
        get() = _countryData

    val globalData : LiveData<Resource<Result>>
        get() = _globalData
    init {
        getAllData()
    }


    private fun getAllData(){
        _countryData.value = Resource.Loading()
        uiScope.launch {
            withContext(Dispatchers.IO){
                val ng = repository.getNigeriaCases("NG")
                val global = repository.getGlobalCases()
                val state = repository.getStateCases()
                _stateData.postValue(state)
                _globalData.postValue(global)
                _countryData.postValue(ng)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}