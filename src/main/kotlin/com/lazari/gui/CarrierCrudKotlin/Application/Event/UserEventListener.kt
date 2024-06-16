package com.lazari.gui.CarrierCrudKotlin.Application.Event

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserEventListener {
    @EventListener
    fun handleCreateUserEvent(event: CreateUserEvent){
        // Implementacão
        println("User Created: ${event.name + event.email}")
    }
}