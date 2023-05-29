package com.axon.udemy.product.core.errorhandling

import java.time.LocalDateTime

data class ErrorMessage(val timestamp: LocalDateTime, val message: String)