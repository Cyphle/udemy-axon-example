package com.axon.udemy.order.query.repositories

import com.axon.udemy.order.query.repositories.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<OrderEntity, String> {
}