package com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository

import com.lazari.gui.CarrierCrudKotlin.Domain.Model.Carrier
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CarrierRepository : ICarrierRepository {
    private val carriers: MutableList<Carrier> = mutableListOf()

    override fun getAll(): List<Carrier> {
        return carriers.toList()
    }

    override fun getById(id: UUID): Carrier? {
        return carriers.find { it.id == id }
    }

    override fun addCarrier(newCarrier:Carrier): Carrier {
        carriers.add(newCarrier)
        return newCarrier
    }
}
