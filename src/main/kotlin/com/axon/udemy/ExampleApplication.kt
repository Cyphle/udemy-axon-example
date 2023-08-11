package com.axon.udemy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AxonExampleApplication

fun main(args: Array<String>) {
	runApplication<AxonExampleApplication>(*args)
}
