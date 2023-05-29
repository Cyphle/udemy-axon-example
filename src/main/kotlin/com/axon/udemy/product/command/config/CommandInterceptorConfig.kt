package com.axon.udemy.product.command.config

import com.axon.udemy.command.infrastructure.interceptors.CreateProductCommandInterceptor
import org.axonframework.commandhandling.CommandBus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration

@Configuration
class CommandInterceptorConfig {
    @Autowired
    fun registerCreateProductCommandInterceptor(context: ApplicationContext, commandBus: CommandBus) {
        val createProductCommandInterceptor = context.getBean(CreateProductCommandInterceptor::class.java)
        commandBus.registerDispatchInterceptor(createProductCommandInterceptor)
    }
}