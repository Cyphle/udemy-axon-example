package com.axon.udemy.config

import org.axonframework.config.EventProcessingConfigurer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration
class EventsErrorHandlerConfig {
    @Autowired
    fun configure(eventProcessingConfigurer: EventProcessingConfigurer) {
        eventProcessingConfigurer.registerListenerInvocationErrorHandler("products-group") { ProductsServiceEventsErrorHandler() }

        // Default Axon error handler
//        eventProcessingConfigurer.registerListenerInvocationErrorHandler("products-group") { PropagatingErrorHandler() }
    }
}