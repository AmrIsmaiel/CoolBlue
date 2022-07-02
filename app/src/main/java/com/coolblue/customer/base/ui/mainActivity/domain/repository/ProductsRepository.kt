package com.coolblue.customer.base.ui.mainActivity.domain.repository

import com.coolblue.customer.base.data.remote.model.PagedResponse
import com.coolblue.customer.base.data.remote.model.Product
import io.reactivex.Observable
import io.reactivex.Single

interface ProductsRepository {
    fun getProducts(query: String, page: Int): Single<PagedResponse<Product>>
}