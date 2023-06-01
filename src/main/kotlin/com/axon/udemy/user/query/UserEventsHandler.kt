package com.axon.udemy.user.query

import com.axon.udemy.user.core.PaymentDetails
import com.axon.udemy.user.core.User
import org.springframework.stereotype.Component

@Component
class UserEventsHandler {
    fun findUserPaymentDetails(query: FetchUserPaymentDetailsQuery): User {
        return User(
            "John",
            "Doe",
            "1234",
            PaymentDetails(
                "John Doe",
                "1234-1234-1234-1234",
                12,
                2030,
                "123"
            )
        )
    }
}