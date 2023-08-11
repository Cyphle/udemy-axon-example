package com.axon.udemy.command.payment.lookup

import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository : JpaRepository<PaymentEntity, String>