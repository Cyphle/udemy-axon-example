package com.axon.udemy.query.order.repositories

import com.axon.udemy.command.order.domain.OrderStatus
import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id
    var orderId: String,
    var productId: String,
    var userId: String,
    var quantity: Int,
    var addressId: String,
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus
) : Serializable {
    constructor(): this("", "", "", 0, "", OrderStatus.REJECTED)
}