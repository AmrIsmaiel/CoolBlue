package com.coolblue.customer.factory

import com.coolblue.customer.base.data.remote.model.SearchRequest

object SearchRequestFactory {
    fun makeSearchRequest() = SearchRequest(
        query = DataFactory.randomString(),
        page = DataFactory.randomInt()
    )
}