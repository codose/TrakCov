package com.harzzy.trakcov.api.response.global

data class GlobalResponse(
    val results: List<Result>,
    val stat: String
)