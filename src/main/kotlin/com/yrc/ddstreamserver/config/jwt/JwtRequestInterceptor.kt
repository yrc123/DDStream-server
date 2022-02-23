package com.yrc.ddstreamserver.config.jwt

import com.yrc.common.service.jwt.JwtService
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtRequestInterceptor(
    private val jwtService: JwtService,
    @Value("\${spring.application.name}") private val issuer: String,
) : RequestInterceptor {
    companion object {
        const val JWS_KEY = "jws"
    }

    override fun apply(request: RequestTemplate) {
        val time = Date(System.currentTimeMillis())
        val data: String = when (request.method()) {
            "GET", "DELETE" -> {
                request.path()
            }
            "POST" -> {
                String(request.body())
            }
            else -> {
                TODO("抛出异常")
            }
        }
        val jws = jwtService.encode(data, 120 * 1000, issuer, time)
        request.header(JWS_KEY, jws)
    }
}