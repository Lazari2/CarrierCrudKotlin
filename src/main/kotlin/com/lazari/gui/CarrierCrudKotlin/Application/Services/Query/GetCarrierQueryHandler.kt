package com.lazari.gui.CarrierCrudKotlin.Application.Services.Query

import com.lazari.gui.CarrierCrudKotlin.Application.Services.ViewModel.CarrierViewModel
import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository.CarrierRepository
import org.springframework.stereotype.Component

@Component
class GetCarrierQueryHandler (private val carrierRepository: CarrierRepository) {

    fun getCarriers(isActive:Boolean): List<CarrierViewModel>{
        val carriers = carrierRepository.getAll()
        val filteredCarriers = if (isActive)
        {
            carriers.filter { it.isActive }
        }
        else
        {
            carriers
        }
        return filteredCarriers.sortedBy { it.name }
            .map { carrier ->
                CarrierViewModel(
                    name = carrier.name,
                    alias = carrier.alias,
                    pickupRequestEmail = carrier.pickupRequestEmail,
                    sendPickupRequestEmail = carrier.sendPickupRequestEmail,
                    isActive = carrier.isActive
                )
            }
    }
}