package com.axon.udemy.product.core.errorhandling

import org.axonframework.commandhandling.CommandExecutionException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@ControllerAdvice
class ProductsServiceErrorHandler {
    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(ex: IllegalStateException, request: WebRequest): ResponseEntity<Any> {
        val errorMessage = ErrorMessage(LocalDateTime.now(), ex.message ?: "Illegal State Exception")
        return ResponseEntity(errorMessage, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val errorMessage = ErrorMessage(LocalDateTime.now(), ex.message ?: "Illegal State Exception")
        return ResponseEntity(errorMessage, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(CommandExecutionException::class)
    fun handleCommandExecutionException(ex: CommandExecutionException, request: WebRequest): ResponseEntity<Any> {
        val errorMessage = ErrorMessage(LocalDateTime.now(), ex.message ?: "Illegal State Exception")
        return ResponseEntity(errorMessage, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}