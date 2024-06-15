package com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository
import com.lazari.gui.CarrierCrudKotlin.Domain.Model.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface UsersRepository : MongoRepository<User, String> {
   fun findByEmail(email:String): Optional<User>
}