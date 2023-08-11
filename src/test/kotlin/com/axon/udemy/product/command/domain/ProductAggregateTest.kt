package com.axon.udemy.product.command.domain

import com.axon.udemy.command.product.domain.CreateProductCommand
import com.axon.udemy.command.product.domain.ProductAggregate
import com.axon.udemy.command.product.domain.events.ProductReservedEvent
import com.axon.udemy.command.product.domain.events.ProductCreatedEvent
import com.axon.udemy.command.product.domain.ReserveProductCommand
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.Test

class ProductAggregateTest {
    private val fixture: FixtureConfiguration<ProductAggregate> = AggregateTestFixture(ProductAggregate::class.java)

    @Test
    fun `should create product`() {
        fixture
            .givenNoPriorActivity()
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
            .`when`(
                ReserveProductCommand(
                    productId = "123",
                    quantity = 5,
                    orderId = "order-1",
                    userId = "user-1"
                )
            )
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

    @Test
    fun `should validate aggregate state`() {
        fixture
            .givenNoPriorActivity()
            .`when`(
                CreateProductCommand(
                    productId = "123",
                    title = "Test",
                    price = 10.0.toBigDecimal(),
                    quantity = 10
                )
            )
            .expectSuccessfulHandlerExecution()
            .expectState { state ->
                assert(state.productId == "123")
                assert(state.title == "Test")
                assert(state.price == 10.0.toBigDecimal())
                assert(state.quantity == 10)
            }
    }
}
