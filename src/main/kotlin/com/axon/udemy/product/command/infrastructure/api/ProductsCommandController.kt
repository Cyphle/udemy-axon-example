package com.axon.udemy.product.command.infrastructure.api

import com.axon.udemy.product.command.domain.CreateProductCommand
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
//        return try {
            val command = CreateProductCommand(
                price = createProductRestModel.price,
                quantity = createProductRestModel.quantity,
                title = createProductRestModel.title,
                productId = UUID.randomUUID().toString()
            )
            return commandGateway.sendAndWait(command)
        // Remplacé par un controlleradvice
//        } catch (ex: Exception) {
//            LOGGER.error(ex.localizedMessage)
//            ex.localizedMessage
//        }
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