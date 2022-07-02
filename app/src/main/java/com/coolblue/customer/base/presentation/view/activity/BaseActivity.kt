package com.coolblue.customer.base.presentation.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.coolblue.customer.base.data.remote.utils.NetworkUtils

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var binding: VB
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = onCreateBinding(layoutInflater)
        setContentView(binding.root)
    }

    val isNetworkConnected: Boolean
        get() = NetworkUtils.haveNetworkConnection(applicationContext)

    abstract fun onCreateBinding(inflater: LayoutInflater): VB
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    public override fun onDestroy() {
        super.onDestroy()
    }
}