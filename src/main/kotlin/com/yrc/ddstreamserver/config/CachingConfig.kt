package com.yrc.ddstreamserver.config

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit


@Configuration
class CachingConfig {
    @Bean
     fun cacheManager(): CacheManager {
        val cacheManager = CaffeineCacheManager()
        cacheManager.registerCustomCache("ffmpeg-service", buildCache(10 * 60))
        cacheManager.registerCustomCache("ffmpeg-ssl", buildCache(10 * 60))
        return cacheManager
    }
    private fun buildCache(ttl: Long, maxSize: Long = 100L): Cache<Any, Any> {
        return Caffeine.newBuilder()
            .expireAfterWrite(ttl, TimeUnit.SECONDS)
            .maximumSize(maxSize)
            .build()
    }
}