package com.axon.udemy.query.product.jpa.repositories

import com.axon.udemy.query.product.jpa.entities.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductEntity, String> {
    fun findByProductId(productId: String): ProductEntity
    fun findByProductIdOrTitle(productId: String, title: String): ProductEntity
}