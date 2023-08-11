package com.axon.udemy.product.command.domain

import com.axon.udemy.shared.commands.ReserveProductCommand
import com.axon.udemy.dependancy.events.ProductReservedEvent
import com.axon.udemy.product.core.events.ProductCreatedEvent
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.Test

class ProductAggregateTest {
    private val fixture: FixtureConfiguration<ProductAggregate> = AggregateTestFixture(ProductAggregate::class.java)

    @Test
    fun `should create product`() {
        fixture
            .given()
            .`when`(
                CreateProductCommand(
                    productId = "123",
                    title = "Test",
                    price = 10.0.toBigDecimal(),
                    quantity = 10
                )
            )
            .expectSuccessfulHandlerExecution()
            .expectEvents(
                ProductCreatedEvent(
                    productId = "123",
                    title = "Test",
                    price = 10.0.toBigDecimal(),
                    quantity = 10
                )
            )
    }

    @Test
    fun `should throw exception when create product with negative price`() {
        fixture
            .given()
            .`when`(
                CreateProductCommand(
                    productId = "123",
                    title = "Test",
                    price = (-10.0).toBigDecimal(),
                    quantity = 10
                )
            )
            .expectExceptionMessage("Price cannot be less or equal to zero")
    }

    @Test
    fun `should reserve a product`() {
        fixture
            .given(
                ProductCreatedEvent(
                    productId = "123",
                    title = "Test",
                    price = 10.0.toBigDecimal(),
                    quantity = 10
                )
            )
            .`when`(ReserveProductCommand(
                productId = "123",
                quantity = 5,
                orderId = "order-1",
                userId = "user-1"
            ))
            .expectSuccessfulHandlerExecution()
            .expectEvents(
                ProductReservedEvent(
                    productId = "123",
                    quantity = 5,
                    orderId = "order-1",
                    userId = "user-1"
                )
            )
    }
}