package com.axon.udemy.product.command.domain

import com.axon.udemy.dependancy.commands.ReserveProductCommand
import com.axon.udemy.dependancy.events.ProductReservedEvent
import com.axon.udemy.product.core.events.ProductCreatedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.lang.IllegalArgumentException
import java.math.BigDecimal

@Aggregate
class ProductAggregate {
    // IMPORTANT : l'aggregate identifier doit être nullable et default à null sinon Axon pense que "" est un identifier
    @AggregateIdentifier
    var productId: String? = null
    var title: String = ""
    var price: BigDecimal = BigDecimal.ZERO
    var quantity: Int = 0

    constructor()

    @CommandHandler
    constructor(createProductCommand: CreateProductCommand): this() {
        if (createProductCommand.price <= BigDecimal.ZERO) {
            throw IllegalArgumentException("Price cannot be less or equal to zero")
        }

        if (createProductCommand.title.isBlank()) {
            throw IllegalArgumentException("Title cannot be blank or null")
        }

        val event = ProductCreatedEvent(
            productId = createProductCommand.productId,
            title = createProductCommand.title,
            price = createProductCommand.price,
            quantity = createProductCommand.quantity
        )

        AggregateLifecycle.apply(event)

//        if (true) {
//            // Meme si l'exception est après AggregateLifecycle.apply, l'event n'est pas envoyé et la transaction est rollback
//            throw Exception("An error took place in the CreateProductCommand @CommandHandler")
//        }
    }

    // Command handler part of the saga
    @CommandHandler
    fun handle(reservedProductCommand: ReserveProductCommand) {
        if (quantity < reservedProductCommand.quantity) {
            throw IllegalArgumentException("Insufficient number of items in stock")
        }

        val productReservedEvent = ProductReservedEvent(
            productId = reservedProductCommand.productId,
            orderId = reservedProductCommand.orderId,
            quantity = reservedProductCommand.quantity,
            userId = reservedProductCommand.userId
        )

        AggregateLifecycle.apply(productReservedEvent)
    }

    @EventSourcingHandler
    fun on(productCreatedEvent: ProductCreatedEvent) {
        productId = productCreatedEvent.productId
        title = productCreatedEvent.title
        price = productCreatedEvent.price
        quantity = productCreatedEvent.quantity
    }

    @EventSourcingHandler
    fun on(productReservedEvent: ProductReservedEvent) {
        quantity -= productReservedEvent.quantity
    }
}