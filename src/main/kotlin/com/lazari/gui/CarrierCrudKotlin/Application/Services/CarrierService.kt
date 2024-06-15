package com.lazari.gui.CarrierCrudKotlin.Application.Services

import com.lazari.gui.CarrierCrudKotlin.Controller.ViewModel.CarrierViewModel
import com.lazari.gui.CarrierCrudKotlin.Domain.Model.Carrier
import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository.CarriersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CarrierService {
    @Autowired
    lateinit var carrierRepository: CarriersRepository

    fun getCarriers(isActive:Boolean): List<CarrierViewModel>{
        val carriers = carrierRepository.findAll()
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
                    alias = carrier.alias ?: "",
                    pickupRequestEmail = carrier.pickupRequestEmail ?: "",
                    sendPickupRequestEmail = carrier.sendPickupRequestEmail,
                    isActive = carrier.isActive
                )
            }
    }

    fun addCarrier(newCarrier: Carrier): Carrier {
        return carrierRepository.save(newCarrier)
    }
}