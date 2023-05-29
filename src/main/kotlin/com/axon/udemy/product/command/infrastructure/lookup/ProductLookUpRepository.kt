package com.axon.udemy.product.command.infrastructure.lookup

import org.springframework.data.jpa.repository.JpaRepository

interface ProductLookUpRepository : JpaRepository<ProductLookUpEntity, String> {
    fun findByProductIdOrTitle(productId: String, title: String): ProductLookUpEntity?
}
