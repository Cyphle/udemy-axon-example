package com.axon.udemy.command.product.infrastructure.api

import com.axon.udemy.command.product.domain.CreateProductCommand
import jakarta.validation.Valid
import org.axonframework.commandhandling.gateway.CommandGateway
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/products")
class ProductsCommandController(private val commandGateway: CommandGateway) {
    @PostMapping
    fun createProduct(
        @Valid @RequestBody createProductRestModel: CreateProductRestModel
    ): String {
            val command = CreateProductCommand(
                price = createProductRestModel.price,
                quantity = createProductRestModel.quantity,
                title = createProductRestModel.title,
                productId = UUID.randomUUID().toString()
            )
            return commandGateway.sendAndWait(command)
    }

    @PutMapping
    fun updateProduct(): String {
        return ""
    }

    @DeleteMapping
    fun deleteMapping(): String {
        return ""
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(ProductsCommandController::class.java)
    }
}