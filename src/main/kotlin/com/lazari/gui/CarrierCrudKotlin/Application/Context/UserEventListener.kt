package com.lazari.gui.CarrierCrudKotlin.Application.Context

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserEventListener {
    @EventListener
    fun handleCreateUserEvent(event: CreateUserEvent){
        // implementar lógica para tratar o evento de criação
        println("User Created: ${event.name + event.email}")
    }
}