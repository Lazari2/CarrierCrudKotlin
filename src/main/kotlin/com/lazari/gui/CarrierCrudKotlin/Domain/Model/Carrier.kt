package com.lazari.gui.CarrierCrudKotlin.Domain.Model


import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "carrier")
class Carrier(
    val id: UUID = UUID.randomUUID(),

    name: String,
    alias: String?,
    pickupRequestEmail: String?,
    sendPickupRequestEmail: Boolean,
    isActive: Boolean
) {
    var name: String = name
        private set
    var alias: String? = alias
        private set
    var pickupRequestEmail: String? = pickupRequestEmail
        private set
    var sendPickupRequestEmail: Boolean = sendPickupRequestEmail
        private set
    var isActive: Boolean = isActive
        private set
}
