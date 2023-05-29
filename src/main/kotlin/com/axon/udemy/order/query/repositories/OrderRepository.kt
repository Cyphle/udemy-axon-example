package com.axon.udemy.query.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<OrderEntity, String> {
}