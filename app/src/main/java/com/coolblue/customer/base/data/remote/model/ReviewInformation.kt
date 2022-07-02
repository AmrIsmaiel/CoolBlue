package com.coolblue.customer.base.data.remote.model

data class ReviewInformation(
    val reviews: List<String>?,
    val reviewSummary: ReviewSummary?
)

data class ReviewSummary(
    val reviewAverage: Double?,
    val reviewCount : Double?
)
