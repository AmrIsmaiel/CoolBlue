package com.coolblue.customer.base.ui.mainActivity.domain.interactor.remote

import com.coolblue.customer.base.data.remote.model.PagedResponse
import com.coolblue.customer.base.data.remote.model.Product
import com.coolblue.customer.base.data.remote.model.SearchRequest
import com.coolblue.customer.base.domain.interactor.SingleUseCase
import com.coolblue.customer.base.ui.mainActivity.domain.repository.ProductsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetProductsUserCase @Inject constructor(
    private val repository: ProductsRepository
) : SingleUseCase<SearchRequest, PagedResponse<Product>>() {
    override fun build(params: SearchRequest): Single<PagedResponse<Product>> {
        return repository.getProducts(params.query, params.page)
    }
}