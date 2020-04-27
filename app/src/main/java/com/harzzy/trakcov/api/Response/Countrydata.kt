package com.harzzy.trakcov.api.Response

data class Countrydata(
    val info: Info,
    val total_active_cases: Int,
    val total_cases: Int,
    val total_danger_rank: Int,
    val total_deaths: Int,
    val total_new_cases_today: Int,
    val total_new_deaths_today: Int,
    val total_recovered: Int,
    val total_serious_cases: Int,
    val total_unresolved: Int
)