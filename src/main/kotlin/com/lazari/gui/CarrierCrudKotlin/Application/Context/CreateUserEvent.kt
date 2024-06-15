package com.lazari.gui.CarrierCrudKotlin.Application.Context


import com.lazari.gui.CarrierCrudKotlin.Domain.Model.User
import org.springframework.context.ApplicationEvent

data class CreateUserEvent (val name:String,
                            val email : String,
                            val password: String):ApplicationEvent(User)
