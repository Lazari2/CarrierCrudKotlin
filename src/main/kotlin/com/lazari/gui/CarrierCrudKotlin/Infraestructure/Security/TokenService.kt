package com.lazari.gui.CarrierCrudKotlin.Infraestructure.Security

import User
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenService {
    @Value("\${api.security.token.secret}")
    private lateinit var secret: String

    fun generateToken(user: User): String {
        try {
            val algorithm = Algorithm.HMAC256(secret)
            val token = JWT.create()
                .withIssuer("login")
                .withSubject(user.email)
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm)
            return token
        } catch (exception: JWTCreationException) {
            throw RuntimeException("Authentification Error", exception)
        }
    }

    fun validateToken(token: String): String? {
        try {
            val algorithm = Algorithm.HMAC256(secret)
            val verifier: JWTVerifier = JWT.require(algorithm)
                .withIssuer("login")
                .build()

            return verifier.verify(token).subject
        } catch (exception: JWTVerificationException) {
            return null
        }
    }
    private fun generateExpirationDate(): Instant{
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.ofHours(-3))
    }
}