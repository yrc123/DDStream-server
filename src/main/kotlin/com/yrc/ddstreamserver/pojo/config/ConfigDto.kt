package com.yrc.ddstreamserver.pojo.config

import com.yrc.ddstreamserver.pojo.config.ApplicationConfiguration.*
import com.yrc.ddstreamserver.pojo.keyvaluestore.KeyValueEntity
import org.valiktor.functions.hasSize
import org.valiktor.functions.isNotNull
import org.valiktor.validate


data class ConfigDto(
    var jwtPublicKey: String? = null,
    var jwtPrivateKey: String? = null,
    var openRegister: Boolean? = null,
) {
    companion object {
        val commonValidator = { configDto: ConfigDto ->
            validate(configDto) {
                validate(ConfigDto::jwtPublicKey)
                    .isNotNull()
                    .hasSize()
            }
        }
    }
    constructor(map: Map<String, String>) : this() {
        jwtPublicKey = map[JWT_PUBLIC_KEY.toString()]
        jwtPrivateKey = map[JWT_PRIVATE_KEY.toString()]
        openRegister = map[OPEN_REGISTER.toString()].toBoolean()
    }
    fun toKeyValueEntityList(): List<KeyValueEntity> {
        return listOf(
            KeyValueEntity(JWT_PUBLIC_KEY.toString(), jwtPublicKey),
            KeyValueEntity(JWT_PRIVATE_KEY.toString(), jwtPrivateKey),
            KeyValueEntity(OPEN_REGISTER.toString(), openRegister.toString()),
        )
    }
}
