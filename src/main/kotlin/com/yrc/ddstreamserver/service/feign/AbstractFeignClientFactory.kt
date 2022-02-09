package com.yrc.ddstreamserver.service.feign

import com.yrc.ddstreamserver.config.jwt.JwtRequestInterceptor
import com.yrc.ddstreamserver.service.client.ClientService
import feign.Client
import feign.Feign
import org.springframework.cache.annotation.Cacheable
import org.springframework.cloud.openfeign.support.SpringDecoder
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.cloud.openfeign.support.SpringMvcContract
import javax.annotation.Resource

abstract class AbstractFeignClientFactory<T>(
    val clazz: Class<T>,
) {
    @Resource
    lateinit var jwtRequestInterceptor: JwtRequestInterceptor
    @Resource
    lateinit var clientService: ClientService
    @Resource
    lateinit var springEncoder: SpringEncoder
    @Resource
    lateinit var springDecoder: SpringDecoder
    @Resource(name = "trustAllClient")
    lateinit var client: Client

    @Cacheable(value = ["ffmpeg-service"], key = "#id")
    open fun getServiceInstance(id: String): T {
        if (!clientService.clientContains(id)) {
            TODO("抛出异常")
        }
        return Feign.builder()
            .client(client)
            .encoder(springEncoder)
            .decoder(springDecoder)
            .contract(SpringMvcContract())
            .requestInterceptor(jwtRequestInterceptor)
            .target(
                clazz,
                clientService.getClientInstance(id)?.uri.toString()
            )
    }
}