package com.harzzy.trakcov.api

import android.content.Context
import android.provider.SyncStateContract
import com.google.gson.GsonBuilder
import com.harzzy.trakcov.utils.TrakCovApplication
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.security.AccessControlContext
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

class RetrofitClient(context: Context) {

        private var mClient: OkHttpClient? = null
        private var mGsonConverter: GsonConverterFactory? = null
        private val cacheSize = (5 * 1024 * 1024).toLong()
        private val myCache = Cache(context.cacheDir, cacheSize)

    private val okHttpClient = OkHttpClient.Builder()
        .cache(myCache)
        .addInterceptor { chain ->
            // Get the request from the chain.
            var request = chain.request()
            request = if (TrakCovApplication.hasNetwork(context)!!){
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            }
            else{
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
            }
            chain.proceed(request)
        }


        private val client: OkHttpClient
            @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
            get() {
                if (mClient == null) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    val httpBuilder = OkHttpClient.Builder()
                    httpBuilder
                        .cache(myCache)
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .addInterceptor(interceptor)  /// show all JSON in logCat
                    mClient = httpBuilder.build()

                }
                return mClient!!
            }

        val gsonConverter: GsonConverterFactory
            get() {
                if (mGsonConverter == null) {
                    mGsonConverter = GsonConverterFactory
                        .create(
                            GsonBuilder()
                                .setLenient()
                                .disableHtmlEscaping()
                                .create()
                        )
                }
                return mGsonConverter!!
            }

        fun covidNewsService() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(gsonConverter)
            .client(okHttpClient.build())
            .build()
        .create(CovidService::class.java)

        fun covidStateService() = Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(gsonConverter)
            .client(okHttpClient.build())
            .build()
            .create(CovidService::class.java)

        fun covidCountryService() = Retrofit.Builder()
            .baseUrl(BASE_URL3)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(gsonConverter)
            .client(okHttpClient.build())
            .build()
            .create(CovidService::class.java)

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        private const val BASE_URL2 = "https://www.trackcorona.live/api/"
        private const val BASE_URL3 = "https://corona.lmao.ninja/v2/"


    }
}