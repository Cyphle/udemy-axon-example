package com.axon.udemy.order.query

import com.axon.udemy.order.query.repositories.OrderRepository
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class OrderQueryHandler(private val orderRepository: OrderRepository) {
    // FindOrderQuery est paramétré en subscription query dans le command controller
    @QueryHandler
    fun handle(query: FindOrderQuery): OrderSummary {
        return orderRepository
            .findById(query.orderId)
            .map { OrderSummary(it.orderId, it.orderStatus.name, "") }
            .orElseThrow { IllegalArgumentException("Order not found") }
    }
}