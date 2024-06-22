package com.lazari.gui.CarrierCrudKotlin.Controller.FrontControllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping



@Controller
class PageController {
    @GetMapping("/frontPage")
    fun frontPage(model:Model): String {
        return "frontPage"
    }
}