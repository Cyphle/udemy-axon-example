package com.axon.udemy.query.order

import com.axon.udemy.query.order.repositories.OrderRepository
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