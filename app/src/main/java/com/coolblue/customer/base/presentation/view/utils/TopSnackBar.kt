package com.coolblue.customer.base.presentation.view.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.core.view.doOnLayout
import androidx.lifecycle.LifecycleOwner


class TopSnackBar(
    lifecycleOwner: LifecycleOwner,
    view: View,
    private val reverse: Boolean = false
) : DismissibleView(lifecycleOwner, view) {

    private val showAnimationDuration = 300L
    private val hideAnimationDuration = 500L

    override fun onViewVisible() {
        super.onViewVisible()
        view.doOnLayout {
            val fromY = if (reverse) view.height.toFloat() else -view.height.toFloat()
            val animate = TranslateAnimation(0f, 0f, fromY, 0f)
            animate.duration = showAnimationDuration
            view.startAnimation(animate)
        }
    }

    override fun hideView() {
        val toY = if (reverse) view.height.toFloat() else -view.height.toFloat()
        val animate = TranslateAnimation(0f, 0f, 0f, toY)
        animate.duration = hideAnimationDuration
        animate.setAnimationListener(
            object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    super@TopSnackBar.hideView()
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }
            }
        )
        view.startAnimation(animate)
    }
}
