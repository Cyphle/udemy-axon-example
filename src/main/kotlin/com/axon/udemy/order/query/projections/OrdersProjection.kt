package com.axon.udemy.order.query.projections

import com.axon.udemy.order.core.OrderCreatedEvent
import com.axon.udemy.order.query.repositories.OrderEntity
import com.axon.udemy.order.query.repositories.OrderRepository
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