package com.yrc.ddstreamserver.config.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.yrc.common.service.jwt.JwtKeyProvider
import com.yrc.common.service.jwt.JwtService
import com.yrc.common.service.jwt.impl.JwtServiceImpl
import com.yrc.ddstreamserver.exception.common.EnumServerException
import com.yrc.ddstreamserver.pojo.config.ApplicationConfiguration.JWT_PRIVATE_KEY
import com.yrc.ddstreamserver.service.keyvaluestore.KeyValueStoreService
import io.jsonwebtoken.io.Decoders
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec

@Configuration
class JwtConfig {
    @Bean
    fun getJwtService(jwtKeyProvider: JwtKeyProvider, objectMapper: ObjectMapper): JwtService {
        return JwtServiceImpl(jwtKeyProvider, objectMapper)
    }

    @Bean
    fun getJwtKeyProvider(keyValueStoreService: KeyValueStoreService): JwtKeyProvider {
        return EncodeJwtKeyProvider(keyValueStoreService)
    }

    class EncodeJwtKeyProvider(private val keyValueStoreService: KeyValueStoreService) : JwtKeyProvider {
        private var _privateKey: PrivateKey? = null

        override fun getPrivateKey(): PrivateKey {
            if (_privateKey == null) {
                _privateKey = selectPrivateKey()
            }
            return _privateKey!!
        }

        override fun getPublicKey(): PublicKey {
            throw EnumServerException.NOT_SUPPORT_DECODE_JWS.build()
        }
        private fun selectPrivateKey(): PrivateKey {
            val encodePrivateKey = keyValueStoreService
                .getById(JWT_PRIVATE_KEY.toString())
                .value ?: throw EnumServerException.JWT_PRIVATE_KEY_NOT_CONTAINS.build()
            val privateKeyString = Decoders.BASE64.decode(encodePrivateKey)
            return KeyFactory
                .getInstance("EC")
                .generatePrivate(PKCS8EncodedKeySpec(privateKeyString))
        }

        override fun reset() {
            synchronized(_privateKey!!) {
                _privateKey = selectPrivateKey()
            }
        }
    }
}