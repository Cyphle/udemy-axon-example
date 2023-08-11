package com.axon.udemy.query.order.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<OrderEntity, String> {
    fun findByOrderId(orderId: String): OrderEntity?
}