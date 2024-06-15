package com.lazari.gui.CarrierCrudKotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
@EntityScan("com.lazari.gui.CarrierCrudKotlin.Domain.Model")
class CarrierCrudKotlinApplication

fun main(args: Array<String>) {
	runApplication<CarrierCrudKotlinApplication>(*args)
}
