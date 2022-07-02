package com.coolblue.customer.base.presentation.view.utils

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

open class DismissibleView(
    private val lifecycleOwner: LifecycleOwner,
    protected val view: View,
    private val onHidden: () -> Unit = {}
) : LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()

    private val showPublishSubject = PublishSubject.create<Boolean>()

    private var shouldBeHidden = false

    private val duration = 5L

    init {
        @Suppress("LeakingThis")
        lifecycleOwner.lifecycle.addObserver(this)

        compositeDisposable.add(
            showPublishSubject
                .switchMap { Single.timer(duration, TimeUnit.SECONDS).toObservable() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ hideInternal() }, {})
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun checkShouldBeHidden() {
        if (shouldBeHidden) {
            hideInternal()
        }
    }

    fun show() {
        if (!view.isVisible) {
            showInternal()
        }
        showPublishSubject.onNext(true)
    }

    fun dismiss() {
        hideInternal()
        compositeDisposable.clear()
    }

    private fun showInternal() {
        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            view.isVisible = true
            onViewVisible()
        }
    }

    protected open fun onViewVisible() {}

    private fun hideInternal() {
        shouldBeHidden = true
        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            hideView()
        }
    }

    protected open fun hideView() {
        onHidden.invoke()
        view.isVisible = false
        shouldBeHidden = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun clear() {
        compositeDisposable.dispose()
    }
}
