package com.axon.udemy.query.projections

import com.axon.udemy.core.OrderCreatedEvent
import com.axon.udemy.query.repositories.OrderEntity
import com.axon.udemy.query.repositories.OrderRepository
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class OrdersProjection(private val orderRepository: OrderRepository) {
    @EventHandler
    fun on(event: OrderCreatedEvent) {
        val orderEntity = OrderEntity(
            orderId = event.orderId,
            productId = event.productId,
            userId = event.userId,
            quantity = event.quantity,
            addressId = event.addressId,
            orderStatus = event.orderStatus
        )

        orderRepository.save(orderEntity)
    }
}