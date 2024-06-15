package com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository

import com.lazari.gui.CarrierCrudKotlin.Domain.Model.Carrier
import org.springframework.data.mongodb.repository.MongoRepository

interface CarriersRepository : MongoRepository<Carrier, String> {
}