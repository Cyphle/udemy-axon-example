package com.axon.udemy.product.command.infrastructure.lookup

import com.axon.udemy.product.core.events.ProductCreatedEvent
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.eventhandling.ResetHandler
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("product-group")
class ProductLookUpEventHandler(private val productLookUpRepository: ProductLookUpRepository) {
    @EventHandler
    fun on(event: ProductCreatedEvent) {
        productLookUpRepository.save(ProductLookUpEntity(
            event.productId,
            event.title
        ))
    }

    @ResetHandler
    fun reset() {
        productLookUpRepository.deleteAll()
    }
}