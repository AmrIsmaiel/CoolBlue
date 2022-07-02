package com.coolblue.customer.base.data.remote.retrofit

import com.coolblue.customer.base.data.remote.model.PagedResponse
import com.coolblue.customer.base.data.remote.model.Product
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("search")
    fun getProducts(
        @Query("query") query: String,
        @Query("page") pageNumber: Int
    ): Single<PagedResponse<Product>>
}