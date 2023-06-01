package com.axon.udemy.order.query.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<OrderEntity, String> {
    fun findByOrderId(orderId: String): OrderEntity?
}