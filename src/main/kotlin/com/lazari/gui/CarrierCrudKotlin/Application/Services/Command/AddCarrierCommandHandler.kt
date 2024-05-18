package com.lazari.gui.CarrierCrudKotlin.Application.Services.Command

import com.lazari.gui.CarrierCrudKotlin.Domain.Model.Carrier
import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository.ICarrierRepository
import org.springframework.stereotype.Component

@Component
class AddCarrierCommandHandler(private val carrierRepository: ICarrierRepository) {

    fun addCarrier(newCarrier: Carrier): Carrier {
        return carrierRepository.addCarrier(newCarrier)
    }
}
