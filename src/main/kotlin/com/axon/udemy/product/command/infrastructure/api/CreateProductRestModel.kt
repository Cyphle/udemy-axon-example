package com.axon.udemy.product.command.infrastructure.api

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

data class CreateProductRestModel(
    @NotBlank(message = "Product title is a required field!")
    val title: String,
    @Min(value = 1, message = "Price cannot be lower than 1")
    val price: BigDecimal,
    @Min(value = 1, message = "Quantity cannot be lower than 1")
    @Max(value = 5, message = "Quantity cannot be larger than 5")
    val quantity: Int
)