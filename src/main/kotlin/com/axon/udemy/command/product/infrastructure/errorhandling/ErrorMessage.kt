package com.axon.udemy.command.product.infrastructure.errorhandling

import java.time.LocalDateTime

data class ErrorMessage(val timestamp: LocalDateTime, val message: String)