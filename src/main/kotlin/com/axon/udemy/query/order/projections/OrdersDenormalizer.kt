package com.axon.udemy.query.order.projections

import com.axon.udemy.command.order.domain.events.OrderApprovedEvent
import com.axon.udemy.command.order.domain.events.OrderCreatedEvent
import com.axon.udemy.query.order.repositories.OrderEntity
import com.axon.udemy.query.order.repositories.OrderRepository
import com.axon.udemy.command.order.domain.events.OrderRejectedEvent
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("orders-group")
class OrdersDenormalizer(private val orderRepository: OrderRepository) {
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

    @EventHandler
    fun on(event: OrderApprovedEvent) {
        val orderEntity = orderRepository.findByOrderId(event.orderId)

        if (orderEntity == null) {
            LOGGER.error("Order with id {} not found", event.orderId)
            return
        }

        orderEntity.orderStatus = event.orderStatus
        orderRepository.save(orderEntity)
    }

    @EventHandler
    fun on(event: OrderRejectedEvent) {
        val orderEntity = orderRepository.findByOrderId(event.orderId)

        if (orderEntity == null) {
            LOGGER.error("Order with id {} not found", event.orderId)
            return
        }

        orderEntity.orderStatus = event.orderStatus
        orderRepository.save(orderEntity)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(OrdersDenormalizer::class.java)
    }
}