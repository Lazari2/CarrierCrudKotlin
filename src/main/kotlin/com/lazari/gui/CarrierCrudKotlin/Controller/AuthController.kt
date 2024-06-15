package com.lazari.gui.CarrierCrudKotlin.Controller

import com.lazari.gui.CarrierCrudKotlin.Application.Context.CreateUserEvent
import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.AuthResponse
import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.LoginRequest
import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.RegisterRequest
import com.lazari.gui.CarrierCrudKotlin.Domain.Model.User
import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository.UsersRepository
import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Security.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var tokenService: TokenService
    @Autowired
    lateinit var publisher: ApplicationEventPublisher

    @GetMapping("/users")
    fun getUsers(): List<User> {
        return usersRepository.findAll()
    }
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
            publisher.publishEvent(CreateUserEvent(newUser.name,
                                                   newUser.email,
                                                   newUser.password))

            val token = tokenService.generateToken(newUser)
            ResponseEntity.ok(AuthResponse(newUser.name, token))
        } else {
            ResponseEntity.badRequest().build()
        }
    }

}
