package com.harzzy.trakcov.api.response.state.nigeria


import com.google.gson.annotations.SerializedName

data class NigeriaResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>
)