package com.axon.udemy.product.query.handlers

import com.axon.udemy.query.api.FindProductsQuery
import com.axon.udemy.query.api.ProductRestModel
import com.axon.udemy.query.jpa.repositories.ProductRepository
import org.axonframework.config.ProcessingGroup
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