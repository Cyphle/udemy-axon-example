package com.axon.udemy.config

import org.axonframework.eventhandling.EventMessage
import org.axonframework.eventhandling.EventMessageHandler
import org.axonframework.eventhandling.ListenerInvocationErrorHandler
import java.lang.Exception

// Cela permet de rollback la transaction entière depuis la command jusqu'à l'event si y a une exception (par exemple ici dans le eventhandler)
class ProductsServiceEventsErrorHandler : ListenerInvocationErrorHandler {
    override fun onError(exception: Exception, event: EventMessage<*>, eventHandler: EventMessageHandler) {
        throw exception
    }
}