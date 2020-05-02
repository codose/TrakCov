package com.harzzy.trakcov.views.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harzzy.trakcov.api.response.continent.ContinentResponse
import com.harzzy.trakcov.api.response.continent.ContinentResponseItem
import com.harzzy.trakcov.api.response.international.InternationalResponseItem
import com.harzzy.trakcov.api.response.news.Article
import com.harzzy.trakcov.api.response.news.NewsResponse
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

class NewsViewModel(context: Context) : ViewModel() {

    private val job = Job()

    private val repository = NetworkRepository(context)
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    private val _newsData = MutableLiveData<Resource<List<Article>>>()

    val newsData : LiveData<Resource<List<Article>>>
        get() = _newsData



    init {
        getNewsData()
    }
    fun getNewsData() {
        _newsData.value = Resource.Loading()
        uiScope.launch {
            _newsData.value = withContext(Dispatchers.Default){
                repository.getNewsHeadline()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}