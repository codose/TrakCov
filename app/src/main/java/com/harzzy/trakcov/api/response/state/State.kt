package com.harzzy.trakcov.api.response.state

data class State(
    val _id: String,
    val casesOnAdmission: Int,
    val confirmedCases: Int,
    val death: Int,
    val discharged: Int,
    val state: String
)