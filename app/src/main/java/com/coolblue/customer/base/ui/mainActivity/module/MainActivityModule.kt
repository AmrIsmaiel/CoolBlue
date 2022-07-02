package com.coolblue.customer.base.ui.mainActivity.module

import com.coolblue.customer.base.data.remote.retrofit.ApiServices
import com.coolblue.customer.base.ui.mainActivity.presentation.viewmodel.MainActivityViewModel
import com.coolblue.customer.base.ui.mainActivity.data.remote.ProductsRemoteDataSource
import com.coolblue.customer.base.ui.mainActivity.domain.interactor.remote.GetProductsUserCase
import com.coolblue.customer.base.ui.mainActivity.domain.repository.ProductsRepository
import com.coolblue.customer.base.ui.mainActivity.domain.repository.ProductsRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class MainActivityModule {

    @Provides
    fun providesProductsRemoteDataSource(apiServices: ApiServices) =
        ProductsRemoteDataSource(apiServices = apiServices)

    @Provides
    fun providesProductsRepository(
        productsRemoteDataSource: ProductsRemoteDataSource
    ): ProductsRepository = ProductsRepositoryImp(productsRemoteDataSource)

    @Provides
    fun providesGetProductsUseCase(repository: ProductsRepository) =
        GetProductsUserCase(repository)

    @Provides
    fun providesMainActivityViewModel(
        getProductsUserCase: GetProductsUserCase
    ) = MainActivityViewModel(getProductsUserCase)
}