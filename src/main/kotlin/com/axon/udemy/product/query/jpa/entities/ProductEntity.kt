package com.axon.udemy.product.query.jpa.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable
import java.math.BigDecimal

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id
    @Column(unique = true)
    var productId: String?,
    @Column(unique = true)
    var title: String?,
    var price: BigDecimal?,
    var quantity: Int
) : Serializable {
    constructor(): this(null, null, null, 0)
}