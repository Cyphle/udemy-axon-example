package com.axon.udemy.query.product.api

import com.axon.udemy.query.product.jpa.entities.ProductEntity
import java.math.BigDecimal

data class ProductRestModel(
    val productId: String,
    val title: String,
    val price: BigDecimal,
    val quantity: Int
) {
    companion object {
        fun from(product: ProductEntity) = ProductRestModel(
            productId = product.productId ?: "",
            title = product.title ?: "",
            price = product.price ?: BigDecimal.ZERO,
            quantity = product.quantity ?: 0
        )
    }
}