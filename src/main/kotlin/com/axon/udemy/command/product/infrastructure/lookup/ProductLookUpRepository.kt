package com.axon.udemy.command.product.infrastructure.lookup

import org.springframework.data.jpa.repository.JpaRepository

interface ProductLookUpRepository : JpaRepository<ProductLookUpEntity, String> {
    fun findByProductIdOrTitle(productId: String, title: String): ProductLookUpEntity?
}
