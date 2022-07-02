package com.coolblue.customer.base.ui.mainActivity.data.remote

import com.coolblue.customer.base.data.remote.model.PagedResponse
import com.coolblue.customer.base.data.remote.model.Product
import com.coolblue.customer.base.data.remote.retrofit.ApiServices
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ProductsRemoteDataSource @Inject constructor(
    private val apiServices: ApiServices
) {

    fun getProducts(query: String, page: Int): Single<PagedResponse<Product>> =
        apiServices.getProducts(query, page)
}