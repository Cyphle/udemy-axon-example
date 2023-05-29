package com.axon.udemy.command.infrastructure.api

import com.axon.udemy.command.domain.CreateOrderCommand
import com.axon.udemy.core.OrderStatus
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/orders")
class OrdersCommandController(private val commandGateway: CommandGateway) {
    @PostMapping
    fun createOrder(
        @RequestBody order: CreateOrderRestModel
    ): String {
        return commandGateway.sendAndWait(CreateOrderCommand(
            orderId = UUID.randomUUID().toString(),
            productId = order.productId,
            userId = "userId",
            quantity = order.quantity,
            addressId = order.addressId,
            orderStatus = OrderStatus.CREATED
        ))
    }
}