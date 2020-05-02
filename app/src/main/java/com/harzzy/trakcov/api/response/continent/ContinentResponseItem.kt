package com.harzzy.trakcov.api.response.continent


import com.google.gson.annotations.SerializedName

data class ContinentResponseItem(
    @SerializedName("active")
    val active: Int,
    @SerializedName("cases")
    val cases: Int,
    @SerializedName("continent")
    val continent: String,
    @SerializedName("countries")
    val countries: List<String>,
    @SerializedName("critical")
    val critical: Int,
    @SerializedName("deaths")
    val deaths: Int,
    @SerializedName("recovered")
    val recovered: Int,
    @SerializedName("todayCases")
    val todayCases: Int,
    @SerializedName("todayDeaths")
    val todayDeaths: Int,
    @SerializedName("updated")
    val updated: Long
)