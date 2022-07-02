package com.coolblue.customer.base.ui.mainActivity.presentation.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import com.coolblue.customer.base.presentation.view.activity.BaseActivity
import com.coolblue.customer.base.presentation.view.utils.ClearableEditText
import com.coolblue.customer.base.presentation.view.utils.TopSnackBar
import com.coolblue.customer.base.presentation.view.utils.hideKeyboard
import com.coolblue.customer.base.ui.mainActivity.presentation.adapter.ProductsAdapter
import com.coolblue.customer.base.ui.mainActivity.presentation.viewmodel.MainActivityViewModel
import com.coolblue.customer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), ClearableEditText.Listener {

    private val mViewModel: MainActivityViewModel by viewModels()
    private lateinit var errorSnackBar: TopSnackBar
    private lateinit var adapter: ProductsAdapter
    private var query: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
        adapter = ProductsAdapter(::fetchNext)
        binding.productsRecyclerView.adapter = adapter
        errorSnackBar = TopSnackBar(this, binding.errorView)
        binding.etSearch.setListener(this)
        binding.btnSearch.setOnClickListener { doSearch() }
        binding.swipeRefreshProducts.setOnRefreshListener { doSearch() }
        binding.etSearch.setOnEditorActionListener { v: TextView?, actionId: Int, _: KeyEvent? ->
            val handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch()
                hideKeyboard()
            }
            handled
        }
    }

    private fun doSearch() {
        hideKeyboard()
        query = binding.etSearch.text.toString()
        binding.welcomeTextView.visibility = View.GONE
        binding.productsRecyclerView.visibility = View.VISIBLE
        if (query.isNotEmpty() && query.isNotBlank()) {
            mViewModel.refreshProducts(query)
        }
    }

    private fun fetchNext() {
        if (mViewModel.pageNumber <= mViewModel.pagesSize) {
            if (query.isNotEmpty() && query.isNotBlank()) {
                mViewModel.getProducts(query)
            }
        }
    }

    private fun setupObservers() {
        mViewModel.mLoading.observe(this) {
            binding.swipeRefreshProducts.isRefreshing = it
        }
        mViewModel.mError.observe(this) {
            setError(it)
        }
        mViewModel.mSuccess.observe(this) {
            adapter.submitList(it)
            binding.productsRecyclerView.adapter = adapter
        }
    }

    private fun setError(error: String) {
        with(binding.errorView) {
            text = error
            errorSnackBar.show()
        }
    }

    override fun onCreateBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun didClearText() {
        binding.etSearch.text?.clear()
        binding.productsRecyclerView.visibility = View.GONE
        binding.welcomeTextView.visibility = View.VISIBLE
    }
}