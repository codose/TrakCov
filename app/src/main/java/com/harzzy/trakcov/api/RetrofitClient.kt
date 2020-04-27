package com.harzzy.trakcov.api

import android.provider.SyncStateContract
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

class RetrofitClient {
    object ApiWorker {
        private var mClient: OkHttpClient? = null
        private var mGsonConverter: GsonConverterFactory? = null

        /**
         * Don't forget to remove Interceptors (or change Logging Level to NONE)
         * in production! Otherwise people will be able to see your request and response on Log Cat.
         */
        val client: OkHttpClient
            @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
            get() {
                if (mClient == null) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    val httpBuilder = OkHttpClient.Builder()
                    httpBuilder
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .addInterceptor(interceptor)  /// show all JSON in logCat
                    mClient = httpBuilder.build()

                }
                return mClient!!
            }

        val gsonConverter: GsonConverterFactory
            get() {
                if(mGsonConverter == null){
                    mGsonConverter = GsonConverterFactory
                        .create(
                            GsonBuilder()
                            .setLenient()
                            .disableHtmlEscaping()
                            .create())
                }
                return mGsonConverter!!
            }
    }

    object ApiService {
        private val TAG = "--ApiService"

        fun covidService() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(ApiWorker.gsonConverter)
            .client(ApiWorker.client)
            .build()
            .create(CovidService::class.java)
    }

    companion object {
        private const val BASE_URL =
            "https://api.thevirustracker.com/"
    }
}