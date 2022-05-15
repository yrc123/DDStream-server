package com.yrc.ddstreamserver.controller.config

import cn.dev33.satoken.annotation.SaCheckPermission
import cn.dev33.satoken.annotation.SaCheckSafe
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.service.jwt.JwtKeyProvider
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.pojo.config.ApplicationConfiguration.*
import com.yrc.ddstreamserver.pojo.config.ConfigDto
import com.yrc.ddstreamserver.pojo.keyvaluestore.KeyValueEntity
import com.yrc.ddstreamserver.pojo.permission.PermissionName.CONFIG_READ
import com.yrc.ddstreamserver.pojo.permission.PermissionName.CONFIG_WRITE
import com.yrc.ddstreamserver.service.keyvaluestore.KeyValueStoreService
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class ConfigController(
    private val keyValueStoreService: KeyValueStoreService,
    private val keyProvider: JwtKeyProvider,
) {
    @SaCheckPermission(CONFIG_READ)
    @GetMapping("/config")
    fun getConfig(): ResponseDto<ConfigDto> {
        return ResponseUtils.successResponse(selectConfig())
    }

    @SaCheckSafe
    @SaCheckPermission(CONFIG_WRITE)
    @GetMapping("/config/key:reset")
    fun updateKeyPair(): ResponseDto<String> {
        val keyPair = Keys.keyPairFor(SignatureAlgorithm.ES512)
        val publicKey = Encoders.BASE64.encode(keyPair.public.encoded)
        val privateKey = Encoders.BASE64.encode(keyPair.private.encoded)
        keyValueStoreService.updateBatchById(listOf(
            KeyValueEntity(JWT_PUBLIC_KEY.toString(), publicKey),
            KeyValueEntity(JWT_PRIVATE_KEY.toString(), privateKey),
        ))
        keyProvider.reset()
        return ResponseUtils.successStringResponse()
    }

    @SaCheckPermission(CONFIG_WRITE)
    @GetMapping("/config/register/{open}")
    fun updateOpenRegister(@PathVariable open: Boolean): ResponseDto<String> {
        keyValueStoreService.updateById(
            KeyValueEntity(OPEN_REGISTER.toString(), open.toString())
        )
        return ResponseUtils.successStringResponse()
    }
    private fun selectConfig(): ConfigDto {
        val configMap = keyValueStoreService.listByIds(
            values()
            .map { it.toString() })
            .filter { it.key != null && it.value != null }
            .mapNotNull { it.key!! to it.value!! }
            .toMap()
        return ConfigDto(configMap)
    }
}