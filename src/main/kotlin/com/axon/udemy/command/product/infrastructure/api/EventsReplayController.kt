package com.axon.udemy.command.product.infrastructure.api

import org.axonframework.config.EventProcessingConfiguration
import org.axonframework.eventhandling.TrackingEventProcessor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/management")
class EventsReplayController(private val eventProcessingConfiguration: EventProcessingConfiguration) {
    // Example : ../product-group/reset
    @PostMapping("/event-processor/{processorName}/reset")
    fun replayEvents(
        @PathVariable(name = "processorName") processorName: String,
    ): ResponseEntity<String> {
        return eventProcessingConfiguration
            .eventProcessor(processorName, TrackingEventProcessor::class.java)
            .map {
                // Lance le replay
                it.shutDown()
                it.resetTokens()
                it.start()

                ResponseEntity.ok("The event processor with a name ${processorName} has been reset")
            }
            .orElse(ResponseEntity.badRequest().body("The processor ${processorName} is not a tracking evnet processor"))
    }
}