package com.coolblue.customer.base.data.remote.model

data class Product(
    val productId: Int,
    val productName: String,
    val reviewInformation: ReviewInformation?,
    val USPs: List<String>?,
    val availabilityState: Int?,
    val salesPriceIncVat: Double?,
    val productImage: String?,
    val coolbluesChoiceInformationTitle: String?,
    val promoIcon: PromoIcon,
    val nextDayDelivery: Boolean
)
