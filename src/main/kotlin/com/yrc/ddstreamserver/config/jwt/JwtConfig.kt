package com.yrc.ddstreamserver.config.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.yrc.common.service.jwt.JwtKeyProvider
import com.yrc.common.service.jwt.JwtService
import com.yrc.common.service.jwt.impl.JwtServiceImpl
import com.yrc.ddstreamserver.pojo.common.ApplicationConfiguration
import com.yrc.ddstreamserver.service.keyvaluestore.KeyValueStoreService
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
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
        private val _privateKey: PrivateKey by lazy {
            if (keyValueStoreService
                    .contains(ApplicationConfiguration.JWT_ENCODE_PUBLIC_KEY.toString())
                && keyValueStoreService
                    .contains(ApplicationConfiguration.JWT_ENCODE_PRIVATE_KEY.toString())
            ) {
                val encodePrivateKey = keyValueStoreService
                    .getById(ApplicationConfiguration.JWT_ENCODE_PRIVATE_KEY.toString())
                    .value
                val privateKeyString = Decoders.BASE64.decode(encodePrivateKey)
                KeyFactory
                    .getInstance("EC")
                    .generatePrivate(PKCS8EncodedKeySpec(privateKeyString))
            } else {
                Keys.keyPairFor(SignatureAlgorithm.ES512).private
//                TODO("生成keypair")
            }
        }

        override fun getPrivateKey(): PrivateKey {
            return _privateKey
        }

        override fun getPublicKey(): PublicKey {
            TODO("Not yet implemented")
        }
    }
}