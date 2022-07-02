package com.coolblue.customer.base.ui.mainActivity.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.coolblue.customer.R
import com.coolblue.customer.base.data.remote.model.Product
import com.coolblue.customer.base.presentation.view.adapter.BaseRecyclerAdapter
import com.coolblue.customer.base.presentation.view.adapter.BaseViewHolder
import com.coolblue.customer.databinding.ItemProductBinding

class ProductsAdapter constructor(
    private val fetchNext: (() -> Unit)? = null
) :
    BaseRecyclerAdapter<Product, ItemProductBinding, ProductsAdapter.ProductViewHolder>(
        ProductsItemDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding = binding, fetchNext = fetchNext)
    }

    inner class ProductViewHolder(
        private val fetchNext: (() -> Unit)? = null,
        private val binding: ItemProductBinding
    ) :
        BaseViewHolder<Product, ItemProductBinding>(binding) {
        override fun bind() {
            if ((currentList.size - 1) - absoluteAdapterPosition == 5)
                fetchNext?.invoke()
            getRowItem()?.let { product ->
                with(binding) {
                    productTitleTextView.text = product.productName
                    productSpecsTextView.text = product.USPs?.joinToString()
                    Glide.with(productImage).load(product.productImage)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_launcher_foreground)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(productImage)
                    reviewAverageTextView.text =
                        String.format(
                            root.resources.getString(R.string.available_count),
                            product.reviewInformation?.reviewSummary?.reviewAverage
                        )

                    reviewTotalTextView.text = String.format(
                        root.resources.getString(R.string.available_count),
                        product.reviewInformation?.reviewSummary?.reviewCount
                    )
                    productPriceTextView.text = String.format(
                        root.resources.getString(R.string.price),
                        product.salesPriceIncVat
                    )
                    nextDayDeliveryTextView.text =
                        String.format(
                            root.resources.getString(R.string.next_day_delivery),
                            if (product.nextDayDelivery) {
                                "Yes"
                            } else {
                                "No"
                            }
                        )
                    productsAvailableCountTextView.text = String.format(
                        root.resources.getString(R.string.available_count),
                        product.availabilityState
                    )
                }
            }
        }
    }
}

class ProductsItemDiffUtil : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}