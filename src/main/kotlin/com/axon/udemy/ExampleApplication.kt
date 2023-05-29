package com.axon.udemy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class UdemyApplication

fun main(args: Array<String>) {
	runApplication<UdemyApplication>(*args)
}
