package com.axon.udemy.product.command.infrastructure.interceptors

import com.axon.udemy.product.command.domain.CreateProductCommand
import com.axon.udemy.product.command.infrastructure.lookup.ProductLookUpRepository
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.messaging.MessageDispatchInterceptor
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class CreateProductCommandInterceptor(private val productLookUpRepository: ProductLookUpRepository) : MessageDispatchInterceptor<CommandMessage<*>> {
    override fun handle(messages: MutableList<out CommandMessage<*>>): BiFunction<Int, CommandMessage<*>, CommandMessage<*>> {
        return BiFunction { index, command ->
            LOGGER.info("Interception of command with payload type: {}", command.payloadType)

            if (command.payloadType.simpleName.contains("CreateProductCommand")) {
                val payload = command.payload as CreateProductCommand

                if (productLookUpRepository.findByProductIdOrTitle(payload.productId, payload.title) != null) {
                    throw IllegalArgumentException("Product with title ${payload.title} already exists")
                }
            }

            command
        }
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor::class.java)
    }
}