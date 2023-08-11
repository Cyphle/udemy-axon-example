package com.axon.udemy.order.saga

import com.axon.udemy.command.order.domain.events.OrderCreatedEvent
import com.axon.udemy.command.order.domain.OrderStatus
import com.axon.udemy.command.saga.OrderSaga
import com.axon.udemy.command.product.domain.events.ProductCreatedEvent
import com.axon.udemy.command.product.domain.ReserveProductCommand
import org.axonframework.test.saga.SagaTestFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderSagaTest {
    private val fixture = SagaTestFixture(OrderSaga::class.java)

    @BeforeEach
    fun setUp() {
        fixture
            .setCallbackBehavior { any, metaData ->
                // Mock callback of command handler
//                if (any is ReserveProductCommand) {

//                } else {

//                }
            }
    }

    @Test
    fun `should place an order`() {
        fixture
            .givenAggregate("product-1")
            .published(ProductCreatedEvent("product-1", "Product 1", BigDecimal(100), 1))
            .whenPublishingA(OrderCreatedEvent("order-1", "product-1", "user-1", 1, "address", OrderStatus.CREATED))
            .expectActiveSagas(1)
            .expectDispatchedCommands(
                ReserveProductCommand("product-1", 1, "order-1", "user-1")
            )
    }

}