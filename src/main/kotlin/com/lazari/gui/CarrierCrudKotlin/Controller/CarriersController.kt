package com.lazari.gui.CarrierCrudKotlin.Controller


import com.lazari.gui.CarrierCrudKotlin.Application.Services.Command.AddCarrierCommandHandler
import com.lazari.gui.CarrierCrudKotlin.Application.Services.Query.GetCarrierQueryHandler
import com.lazari.gui.CarrierCrudKotlin.Application.Services.ViewModel.CarrierViewModel
import com.lazari.gui.CarrierCrudKotlin.Domain.Model.Carrier
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/carriers")
class CarriersController(
    private val getCarrierQueryHandler: GetCarrierQueryHandler,
    private val addCarrierCommandHandler: AddCarrierCommandHandler
) {

    @GetMapping
    fun getCarrier(): List<CarrierViewModel> {
        return getCarrierQueryHandler.getCarriers(isActive = true)
    }

    @PostMapping
    fun postCarrier(@RequestBody newCarrier: Carrier): Carrier {
        return addCarrierCommandHandler.addCarrier(newCarrier)
    }
}
