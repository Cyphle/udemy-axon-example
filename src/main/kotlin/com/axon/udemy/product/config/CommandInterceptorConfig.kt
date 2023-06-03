package com.axon.udemy.product.config

import com.axon.udemy.product.command.infrastructure.interceptors.CreateProductCommandInterceptor
import org.axonframework.commandhandling.CommandBus
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition
import org.axonframework.eventsourcing.SnapshotTriggerDefinition
import org.axonframework.eventsourcing.Snapshotter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommandInterceptorConfig {
    @Autowired
    fun registerCreateProductCommandInterceptor(context: ApplicationContext, commandBus: CommandBus) {
        val createProductCommandInterceptor = context.getBean(CreateProductCommandInterceptor::class.java)
        commandBus.registerDispatchInterceptor(createProductCommandInterceptor)
    }

    // Definition of snapshot mechanism (can have multiple)
    @Bean(name = ["productSnapshotTriggerDefinition"])
    fun productSnapshotTriggerDefinition(snapshotter: Snapshotter): SnapshotTriggerDefinition {
        // 3 is the number of events after to create a snapshot
        return EventCountSnapshotTriggerDefinition(snapshotter, 3)
    }
}