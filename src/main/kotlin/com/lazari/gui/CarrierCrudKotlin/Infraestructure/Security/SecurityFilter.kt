package com.lazari.gui.CarrierCrudKotlin.Infraestructure.Security

import com.lazari.gui.CarrierCrudKotlin.Infraestructure.Repository.UsersRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class SecurityFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var tokenService: TokenService

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = recoverToken(request) ?:""
        val login = tokenService.validateToken(token)

        if (login != null) {
            val user = usersRepository.findByEmail(login)
                .orElseThrow{RuntimeException("Not Found")}
            val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))
            val authentication = UsernamePasswordAuthenticationToken(user, null, authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun recoverToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null) return null
        return authHeader.replace("Bearer ", "").trim()
    }
}
