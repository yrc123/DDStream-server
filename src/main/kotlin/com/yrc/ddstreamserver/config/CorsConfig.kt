package com.yrc.ddstreamserver.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@Configuration
class CorsConfig {
    private fun buildConfig(): CorsConfiguration {
        val corsConfiguration = CorsConfiguration()
        val allowedOriginPatterns: MutableList<String> = ArrayList()
        allowedOriginPatterns.add("*")
        corsConfiguration.allowedOriginPatterns = allowedOriginPatterns
        corsConfiguration.addAllowedHeader("*")
        corsConfiguration.addAllowedMethod("*")
        corsConfiguration.allowCredentials = true
        corsConfiguration.exposedHeaders = listOf("jwt")
        return corsConfiguration
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", buildConfig())
        return CorsFilter(source)
    }
}