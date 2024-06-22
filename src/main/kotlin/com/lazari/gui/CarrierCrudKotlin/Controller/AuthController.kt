package com.lazari.gui.CarrierCrudKotlin.Controller

import com.lazari.gui.CarrierCrudKotlin.Application.DTO.UserDTO
import com.lazari.gui.CarrierCrudKotlin.Application.Event.CreateUserEvent
import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.AuthResponse
import com.lazari.gui.CarrierCrudKotlin.Controller.Requests.LoginRequest
import com.lazari.gui.CarrierCrudKotlin.Domain.Model.User
import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository.UsersRepository
import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Security.TokenService
import org.slf4j.LoggerFactory
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

    private val logger = LoggerFactory.getLogger(AuthController::class.java)

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
            logger.info("Token gerado para {}: {}", user.email, token)
            ResponseEntity.ok(mapOf("name" to user.name, "token" to token, "redirect" to "/frontPage"))
        } else {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody userDTO: UserDTO): ResponseEntity<Any> {
        val user = usersRepository.findByEmail(userDTO.email)

        return if (user.isEmpty) {
            val newUser = User.create(
                name = userDTO.name,
                email = userDTO.email,
                password = passwordEncoder.encode(userDTO.password)
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
