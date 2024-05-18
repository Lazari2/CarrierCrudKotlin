package com.lazari.gui.CarrierCrudKotlin.Domain.Model

import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.UUID

@Table(name ="carrier")
@Entity(name="carrier")

class Carrier {

    val  id : UUID = UUID.randomUUID()
    var name: String = ""
        private set
    var alias : String = ""
        private set
    var pickupRequestEmail: String = ""
        private set
    var sendPickupRequestEmail: Boolean = false
        private set
    var isActive: Boolean = false
        private set
    }

