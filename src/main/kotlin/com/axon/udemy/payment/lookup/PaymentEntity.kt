package com.axon.udemy.payment.lookup

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(name = "payments")
data class PaymentEntity(
    @Id
    val paymentId: String? = null,
    @Column
    var orderId: String? = null
) : Serializable