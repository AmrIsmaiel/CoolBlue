package com.coolblue.customer.data.remote

import com.coolblue.customer.base.data.remote.model.PagedResponse
import com.coolblue.customer.base.data.remote.model.Product
import com.coolblue.customer.base.data.remote.retrofit.ApiServices
import com.coolblue.customer.base.ui.mainActivity.data.remote.ProductsRemoteDataSource
import com.coolblue.customer.factory.ProductFactory
import com.coolblue.customer.factory.SearchRequestFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.kotlintest.mock.mock
import io.reactivex.Single
import org.junit.Test

class ProductsRemoteDataSourceTest {
    private val apiServices = mock<ApiServices>()
    private val productsRemoteDataSource = ProductsRemoteDataSource(apiServices)

    @Test
    fun `getProducts call api with correct queries`() {
        val searchRequest = SearchRequestFactory.makeSearchRequest()
        stubCreateOrder(Single.never())
        productsRemoteDataSource.getProducts(
            searchRequest.query,
            searchRequest.page
        ).test()

        verify(apiServices).getProducts(searchRequest.query, searchRequest.page)
    }

    @Test
    fun `getProducts call api with correct params and return data`() {
        val searchRequest = SearchRequestFactory.makeSearchRequest()
        val pagedResponse = ProductFactory.makePagedResponse()

        stubCreateOrder(Single.just(pagedResponse))
        val testObserver = productsRemoteDataSource.getProducts(
            searchRequest.query,
            searchRequest.page
        ).test()

        testObserver.assertValue(pagedResponse)
    }

    @Test
    fun `getProducts call api with correct params and throw exception when stream emit error`() {
        val searchRequest = SearchRequestFactory.makeSearchRequest()
        val httpException = RuntimeException()
        stubCreateOrder(Single.error(httpException))
        val testObserver = productsRemoteDataSource.getProducts(
            searchRequest.query,
            searchRequest.page
        ).test()

        testObserver.assertError(httpException)
    }

    private fun stubCreateOrder(single: Single<PagedResponse<Product>>) {
        whenever(apiServices.getProducts(any(), any())).thenReturn(single)
    }
}