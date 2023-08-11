package com.axon.udemy.command.user.domain

data class User(
    val firstName: String,
    val lastName: String,
    val userId: String,
    val paymentDetails: PaymentDetails
)