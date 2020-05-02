package com.harzzy.trakcov.utils

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.pixplicity.easyprefs.library.Prefs

/* Created by : Osemwingie Oshodin (codose)
*
* Date : 27th April, 2020
*
* */

class TrakCovApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        initPrefLib()
    }

    private fun initPrefLib() {
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }
    companion object{
        fun hasNetwork(context: Context): Boolean? {
            var isConnected: Boolean? = false // Initial Value
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected
        }
    }
}