package com.lazari.gui.CarrierCrudKotlin.Controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/auth")
class WebAuthController {

    @GetMapping("/login")
    fun showLoginPage(model: Model): String {
        return "loginScreen"
    }

    @GetMapping("/register")
    fun showRegisterPage(model: Model): String {
        return "registerScreen"
    }
}
