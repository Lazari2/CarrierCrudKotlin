package com.lazari.gui.CarrierCrudKotlin.Application.Services

import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

//@Component
@Service
class CustomUsersService : UserDetailsService {

    @Autowired
    lateinit var repository: UsersRepository

    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository.findByEmail(username)
            .orElseThrow { UsernameNotFoundException("User Not Found") }

        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            ArrayList()
        )
    }
}
