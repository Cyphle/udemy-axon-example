package com.axon.udemy.query.product.handlers

import com.axon.udemy.query.product.api.FindProductsQuery
import com.axon.udemy.query.product.api.ProductRestModel
import com.axon.udemy.query.product.jpa.repositories.ProductRepository
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class ProductQueryHandler(private val productRepository: ProductRepository) {
    @QueryHandler
    fun findProducts(query: FindProductsQuery): List<ProductRestModel> {
        return productRepository
            .findAll()
            .map { ProductRestModel.from(it) }
    }
}