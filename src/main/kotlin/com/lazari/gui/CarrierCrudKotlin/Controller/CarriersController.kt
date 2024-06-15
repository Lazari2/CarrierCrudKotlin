package com.lazari.gui.CarrierCrudKotlin.Controller

import com.lazari.gui.CarrierCrudKotlin.Application.Services.CarrierService
import com.lazari.gui.CarrierCrudKotlin.Controller.ViewModel.CarrierViewModel
import com.lazari.gui.CarrierCrudKotlin.Domain.Model.Carrier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/carriers")
class CarriersController() {

    @Autowired
    lateinit var carrierService: CarrierService

    @GetMapping
    fun getCarrier(): List<CarrierViewModel> {
        return carrierService.getCarriers(isActive = true)
    }

    @PostMapping
    fun postCarrier(@RequestBody newCarrier: Carrier): Carrier {
        return carrierService.addCarrier(newCarrier)
    }
}
