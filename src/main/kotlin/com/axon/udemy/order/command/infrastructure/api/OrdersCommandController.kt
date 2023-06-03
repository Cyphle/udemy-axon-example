package com.axon.udemy.order.command.infrastructure.api

import com.axon.udemy.order.command.domain.CreateOrderCommand
import com.axon.udemy.order.core.OrderStatus
import com.axon.udemy.order.query.FindOrderQuery
import com.axon.udemy.order.query.OrderSummary
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/orders")
class OrdersCommandController(
    private val commandGateway: CommandGateway,
    private val queryGateway: QueryGateway
) {
    @PostMapping
    fun createOrder(
        @RequestBody order: CreateOrderRestModel
    ): OrderSummary? {
        val orderId = UUID.randomUUID().toString()

        val queryResult = queryGateway.subscriptionQuery(FindOrderQuery(orderId), ResponseTypes.instanceOf(OrderSummary::class.java), ResponseTypes.instanceOf(OrderSummary::class.java))

        // Use est try with ressource qui appelle queryResult.close à la fin (équivalent d'un block try finally)
        queryResult
            .use { queryResult ->
            commandGateway.sendAndWait<CreateOrderCommand>(CreateOrderCommand(
                orderId = orderId,
                productId = order.productId,
                userId = "userId",
                quantity = order.quantity,
                addressId = order.addressId,
                orderStatus = OrderStatus.CREATED
            ))

            // Subscription query retourne les updates de la projection
            return queryResult.updates().blockFirst()
        }
    }
}