package com.coolblue.customer.base.ui.mainActivity.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.coolblue.customer.base.data.remote.model.PagedResponse
import com.coolblue.customer.base.data.remote.model.Product
import com.coolblue.customer.base.data.remote.model.SearchRequest
import com.coolblue.customer.base.presentation.view.utils.addIfNotExist
import com.coolblue.customer.base.presentation.viewmodel.BaseViewModel
import com.coolblue.customer.base.ui.mainActivity.domain.interactor.remote.GetProductsUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getProductsUserCase: GetProductsUserCase
) : BaseViewModel() {

    val mLoading = MutableLiveData<Boolean>()
    val mSuccess = MutableLiveData<List<Product>>()
    val mError = MutableLiveData<String>()

    private val products: MutableList<Product> = mutableListOf()
    var pageNumber = 1
    var pagesSize = 0

    fun getProducts(query: String) {
        addDisposable(
            getProductsUserCase.build(SearchRequest(query, pageNumber))
                .doOnSubscribe { mLoading.postValue(true) }
                .doFinally { mLoading.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(ProductsSubscriber())
        )
    }

    private inner class ProductsSubscriber : DisposableSingleObserver<PagedResponse<Product>>() {
        override fun onSuccess(it: PagedResponse<Product>) {
            pageNumber = it.currentPage
            pagesSize = it.pageCount
            if (pageNumber <= it.pageCount) {
                pageNumber += 1
            }
            if (it.products.isNotEmpty()) {
                it.products.forEach { product ->
                    products.addIfNotExist(product)
                }
                mSuccess.postValue(products)
            } else {
                mError.value = "There is no products here"
            }
        }
        override fun onError(e: Throwable) {
            mError.postValue(e.message)
        }

    }

    fun refreshProducts(query: String) {
        products.clear()
        this.pageNumber = 1
        getProducts(query)
    }
}