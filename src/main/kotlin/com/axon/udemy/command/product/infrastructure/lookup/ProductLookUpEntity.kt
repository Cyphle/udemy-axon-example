package com.axon.udemy.command.product.infrastructure.lookup

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable

// Cette entité est pour les validations comme "est-ce que le produit n'existe pas déjà ?"
@Entity
@Table(name = "productlookups")
data class ProductLookUpEntity(
    @Id
    val productId: String,
    @Column(unique = true)
    val title: String
) : Serializable {
    constructor(): this("", "")
}