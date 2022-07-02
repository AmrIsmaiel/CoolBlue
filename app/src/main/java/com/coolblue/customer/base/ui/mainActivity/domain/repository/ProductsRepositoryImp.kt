package com.coolblue.customer.base.ui.mainActivity.domain.repository

import com.coolblue.customer.base.data.remote.model.PagedResponse
import com.coolblue.customer.base.data.remote.model.Product
import com.coolblue.customer.base.ui.mainActivity.data.remote.ProductsRemoteDataSource
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ProductsRepositoryImp @Inject constructor(
    private val productsRemoteDataSource: ProductsRemoteDataSource
) : ProductsRepository {
    override fun getProducts(query: String, page: Int): Single<PagedResponse<Product>> =
        productsRemoteDataSource.getProducts(query, page)
}