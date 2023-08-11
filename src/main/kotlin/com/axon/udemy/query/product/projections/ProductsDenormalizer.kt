package com.axon.udemy.query.product.projections

import com.axon.udemy.command.product.domain.events.ProductReservedEvent
import com.axon.udemy.command.product.domain.events.ProductCreatedEvent
import com.axon.udemy.command.product.domain.events.ProductReservationCancelledEvent
import com.axon.udemy.query.product.jpa.entities.ProductEntity
import com.axon.udemy.query.product.jpa.repositories.ProductRepository
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.eventhandling.ResetHandler
import org.axonframework.messaging.interceptors.ExceptionHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("product-group")
class ProductsDenormalizer(private val productRepository: ProductRepository) {
    @ExceptionHandler(resultType = IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException) {

    }

    @ExceptionHandler(resultType = Exception::class)
    fun handleException(ex: Exception) {
        throw ex
    }

    @EventHandler
    fun on(event: ProductCreatedEvent) {
        val productEntity = ProductEntity(
            productId = event.productId,
            title = event.title,
            price = event.price,
            quantity = event.quantity
        )

        try {
            productRepository.save(productEntity)
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        }

//        if (true) {
//            throw Exception("Forcing exception in the Event handler class")
//        }
    }

    @EventHandler
    fun on(event: ProductReservedEvent) {
        val productEntity = productRepository.findByProductId(event.productId)
        productEntity.quantity -= event.quantity
        productRepository.save(productEntity)

        LOGGER.info("ProductReservedEvent is called for orderId {} and productId {}", event.orderId, event.productId)
    }

    @EventHandler
    fun on(event: ProductReservationCancelledEvent) {
        val productEntity = productRepository.findByProductId(event.productId)
        productEntity.quantity += event.quantity
        productRepository.save(productEntity)

        LOGGER.info("ProductReservationCancelledEvent is called for orderId {} and productId {}", event.orderId, event.productId)
    }

    @ResetHandler
    fun reset() {
        productRepository.deleteAll()
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(ProductsDenormalizer::class.java)
    }
}