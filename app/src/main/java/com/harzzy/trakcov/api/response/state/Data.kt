package com.harzzy.trakcov.api.response.state

data class Data(
    val death: Int,
    val discharged: Int,
    val states: List<State>,
    val totalConfirmedCases: Int,
    val totalSamplesTested: String
)