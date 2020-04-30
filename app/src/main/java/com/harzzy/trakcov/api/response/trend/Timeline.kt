package com.harzzy.trakcov.api.response.trend


import com.google.gson.annotations.SerializedName

data class Timeline(
    @SerializedName("cases")
    val cases: HashMap<String,Int>,
    @SerializedName("deaths")
    val deaths: HashMap<String,Int>,
    @SerializedName("recovered")
    val recovered: HashMap<String,Int>
)