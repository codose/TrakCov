package com.harzzy.trakcov.utils

/* Created by : Osemwingie Oshodin (codose)
*
* Date : 27th April, 2020
*
* */

sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val message: String) : Resource<T>()
}