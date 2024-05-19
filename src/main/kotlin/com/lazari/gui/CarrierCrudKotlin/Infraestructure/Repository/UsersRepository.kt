package com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository
import User
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional

interface UsersRepository : JpaRepository<User, String> {
   fun findByEmail(email:String): Optional<User>
}