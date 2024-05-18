package com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository


import com.lazari.gui.CarrierCrudKotlin.Domain.Model.Carrier
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ICarrierRepository {
    fun getAll(): List<Carrier>
    fun getById(id: UUID): Carrier?
    fun addCarrier(newCarrier: Carrier): Carrier
}