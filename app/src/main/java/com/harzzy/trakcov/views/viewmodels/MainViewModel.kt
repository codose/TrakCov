package com.harzzy.trakcov.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harzzy.trakcov.api.Response.Countrydata
import com.harzzy.trakcov.api.RetrofitClient
import com.harzzy.trakcov.dataStoreArchitechture.NetworkRepository
import com.harzzy.trakcov.utils.Resource
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    private val job = Job()

    private val repository = NetworkRepository()
    val uiScope = CoroutineScope(job + Dispatchers.Main)

    private val _countryData = MutableLiveData<Resource<Countrydata>>()

    val countryData : LiveData<Resource<Countrydata>>
        get() = _countryData

    init {
        getData()
    }


    private fun getData (){
        _countryData.value = Resource.Loading()
        uiScope.launch {
            withContext(Dispatchers.IO){
                _countryData.postValue(repository.getCases())
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}