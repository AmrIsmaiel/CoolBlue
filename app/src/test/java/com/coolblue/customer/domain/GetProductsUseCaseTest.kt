package com.coolblue.customer.domain

import com.coolblue.customer.base.data.remote.model.PagedResponse
import com.coolblue.customer.base.data.remote.model.Product
import com.coolblue.customer.base.ui.mainActivity.domain.interactor.remote.GetProductsUserCase
import com.coolblue.customer.base.ui.mainActivity.domain.repository.ProductsRepository
import com.coolblue.customer.factory.ProductFactory
import com.coolblue.customer.factory.SearchRequestFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.kotlintest.mock.mock
import io.reactivex.Single
import org.junit.Test

class GetProductsUseCaseTest {
    private val repository = mock<ProductsRepository>()
    private val getProductsUserCase = GetProductsUserCase(repository)

    @Test
    fun `if build params with correct params necessary functions get called`() {
        val searchRequest = SearchRequestFactory.makeSearchRequest()
        val pagedResponse = ProductFactory.makePagedResponse()

        stubGetProductsSingle(Single.just(pagedResponse))
        getProductsUserCase.build(searchRequest).test()
        verify(repository).getProducts(searchRequest.query, searchRequest.page)
    }

    @Test
    fun `if build params with correct params correct data is returned`() {
        val searchRequest = SearchRequestFactory.makeSearchRequest()
        val pagedResponse = ProductFactory.makePagedResponse()
        stubGetProductsSingle(Single.just(pagedResponse))
        val testObserver = getProductsUserCase.build(searchRequest).test()
        testObserver.assertValue(pagedResponse)
    }

    @Test
    fun `error is thrown when build use case emits error`() {
        val searchRequest = SearchRequestFactory.makeSearchRequest()
        val error = Throwable()
        stubGetProductsSingle(Single.error(error))
        val tesObserver = getProductsUserCase.build(searchRequest).test()
        tesObserver.assertError(error)
    }

    private fun stubGetProductsSingle(single: Single<PagedResponse<Product>>) {
        whenever(repository.getProducts(any(), any()))
            .thenReturn(single)
    }
}