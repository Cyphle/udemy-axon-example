package com.axon.udemy.product.query.api

import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductQueryController(private val queryGateway: QueryGateway) {
    @GetMapping
    fun getProducts(): List<ProductRestModel> {
        val query = FindProductsQuery()

        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(ProductRestModel::class.java)).join()
    }
}