package com.coolblue.customer.factory

import com.coolblue.customer.base.data.remote.model.*

object ProductFactory {

    fun makeProduct() = Product(
        productId = DataFactory.randomInt(),
        productName = DataFactory.randomString(),
        reviewInformation = makeReviewInformation(),
        USPs = arrayListOf(DataFactory.randomString()),
        availabilityState = DataFactory.randomInt(),
        salesPriceIncVat = DataFactory.randomDouble(),
        productImage = DataFactory.randomString(),
        coolbluesChoiceInformationTitle = DataFactory.randomString(),
        promoIcon = makePromoIcon(),
        nextDayDelivery = DataFactory.randomBoolean()
    )

    private fun makeReviewInformation() = ReviewInformation(
        reviews = arrayListOf(DataFactory.randomString()),
        reviewSummary = makeReviewSummary()
    )

    private fun makeReviewSummary() = ReviewSummary(
        reviewAverage = DataFactory.randomDouble(),
        reviewCount = DataFactory.randomDouble()
    )

    private fun makePromoIcon() = PromoIcon(
        text = DataFactory.randomString(),
        type = DataFactory.randomString()
    )

    private fun makeProductList() = mutableListOf(makeProduct())

    fun makePagedResponse() = PagedResponse(
        products = makeProductList(),
        currentPage = DataFactory.randomInt(),
        pageSize = DataFactory.randomInt(),
        totalResults = DataFactory.randomInt(),
        pageCount = DataFactory.randomInt()
    )
}