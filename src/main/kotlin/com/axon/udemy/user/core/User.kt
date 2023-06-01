package com.axon.udemy.user.core

data class User(
    val firstName: String,
    val lastName: String,
    val userId: String,
    val paymentDetails: PaymentDetails
)