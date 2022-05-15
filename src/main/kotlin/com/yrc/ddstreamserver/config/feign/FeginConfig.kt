package com.yrc.ddstreamserver.config.feign

import feign.Client
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.TrustStrategy
import org.apache.http.ssl.SSLContexts
import org.springframework.beans.factory.ObjectFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer
import org.springframework.cloud.openfeign.support.SpringDecoder
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory


@Component
class FeginConfig {
    @Bean(name = ["trustAllClient"])
    fun getFeignClient(): Client {
        return Client.Default(getSslContextFactory(), NoopHostnameVerifier())
    }

    fun getSslContextFactory(): SSLSocketFactory {
        val acceptingTrustStrategy: TrustStrategy = TrustStrategy { chain, authType -> true }
        val sslContext: SSLContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build()
        return sslContext.socketFactory
    }

    @Bean
    fun getSpringEncoder(messageConverters: ObjectFactory<HttpMessageConverters>): SpringEncoder {
        return SpringEncoder(messageConverters)
    }

    @Bean
    fun getSpringDecoder(
        messageConverters: ObjectFactory<HttpMessageConverters>,
        customizers: ObjectProvider<HttpMessageConverterCustomizer>
    ): SpringDecoder {
        return SpringDecoder(messageConverters, customizers)
    }
}