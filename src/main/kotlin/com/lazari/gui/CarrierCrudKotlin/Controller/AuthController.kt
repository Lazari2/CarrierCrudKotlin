package com.lazari.gui.CarrierCrudKotlin.Controller

import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.AuthResponse
import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.LoginRequest
import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.RegisterRequest
import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository.UsersRepository
import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Security.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var tokenService: TokenService

    @PostMapping("/login")
    fun login(@RequestBody body: LoginRequest): ResponseEntity<Any> {
        val user = usersRepository.findByEmail(body.email)
            .orElseThrow { RuntimeException("User not Found") }
        return if (passwordEncoder.matches(body.password, user.password)) {
            val token = tokenService.generateToken(user)
            ResponseEntity.ok(AuthResponse(user.name, token))
        } else {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody body: RegisterRequest): ResponseEntity<Any> {
        val user = usersRepository.findByEmail(body.email)

        return if (user.isEmpty) {
            val newUser = User.create(
                name = body.name,
                email = body.email,
                password = passwordEncoder.encode(body.password)
            )
            usersRepository.save(newUser)

            val token = tokenService.generateToken(newUser)
            ResponseEntity.ok(AuthResponse(newUser.name, token))
        } else {
            ResponseEntity.badRequest().build()
        }
    }

}
