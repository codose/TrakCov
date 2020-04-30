package com.harzzy.trakcov.api.response.trend


import com.google.gson.annotations.SerializedName

data class TrendResponse(
    @SerializedName("country")
    val country: String,
    @SerializedName("provinces")
    val provinces: List<String>,
    @SerializedName("timeline")
    val timeline: Timeline
)