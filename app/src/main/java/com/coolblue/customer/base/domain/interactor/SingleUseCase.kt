package com.coolblue.customer.base.domain.interactor

import io.reactivex.Single

abstract class SingleUseCase<in Params, Type> where Type : Any {
    abstract fun build(params: Params): Single<Type>
}