package com.lazari.gui.CarrierCrudKotlin.Domain.Model
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "users")
class User(
    val id: UUID = UUID.randomUUID(),

    name: String,
    email: String,
    password: String
) {
    var name: String = name
        private set

    var email: String = email
        private set

    var password: String = password
        private set

    companion object {
        fun create(name: String, email: String, password: String): User {
            return User(name = name, email = email, password = password)
        }
    }
}
