package com.harzzy.trakcov.api.response.news


import com.google.gson.annotations.SerializedName
import com.harzzy.trakcov.api.response.news.Article

data class NewsResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)