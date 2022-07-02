package com.coolblue.customer.base.presentation.view.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun <T> MutableList<T>.addIfNotExist(item: T?) {
    if (item != null && !this.contains(item)) this.add(item)
}

fun Activity.hideKeyboard() {
    val inputMethodManager: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    // Find the currently focused view, so we can grab the correct window token from it.
    var view: View? = currentFocus
    // If no view currently has focus, create a new one, just so we can grab a window token from it.
    if (view == null) {
        view = View(this)
    }
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun RecyclerView.onEndReached(block: () -> Unit) {
    addOnScrollListener(
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
// In dx we only care about the negative values because our horizontal is RTL.
                if (dy > 0 || dx < 0) {
                    val visibleItemCount = recyclerView.layoutManager!!.childCount
                    val totalCount = recyclerView.layoutManager!!.itemCount
                    val firstVisibleItems =
                        (recyclerView.layoutManager!! as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (visibleItemCount + firstVisibleItems == totalCount) {
                        block()
                    }
                }
            }
        }
    )
}