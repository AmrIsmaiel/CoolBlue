package com.coolblue.customer.base.data.remote.model

data class PagedResponse<T>(
    val products: MutableList<T>,
    val currentPage: Int,
    val pageSize: Int,
    val totalResults: Int,
    val pageCount: Int
)
