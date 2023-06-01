package com.axon.udemy.user.query.api

import com.axon.udemy.user.core.User
import com.axon.udemy.user.query.FetchUserPaymentDetailsQuery
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UsersQueryController(private val queryGateway: QueryGateway) {
    @GetMapping("/{userId}/payment-details")
    fun getUserPaymentDetails(@PathVariable userId: String): User {
        return queryGateway.query(FetchUserPaymentDetailsQuery(userId), ResponseTypes.instanceOf(User::class.java)).join()
    }
}