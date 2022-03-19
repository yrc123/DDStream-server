package com.yrc.ddstreamserver.controller.config

import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.service.jwt.JwtKeyProvider
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.pojo.config.ApplicationConfiguration.*
import com.yrc.ddstreamserver.pojo.config.ConfigDto
import com.yrc.ddstreamserver.pojo.keyvaluestore.KeyValueEntity
import com.yrc.ddstreamserver.service.keyvaluestore.KeyValueStoreService
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ConfigController(
    private val keyValueStoreService: KeyValueStoreService,
    private val keyProvider: JwtKeyProvider,
) {
    @GetMapping("/config")
    fun getConfig(): ResponseDto<ConfigDto> {
        return ResponseUtils.successResponse(selectConfig())
    }
    @PatchMapping("/config")
    fun updateConfig(configDto: ConfigDto): ResponseDto<ConfigDto> {
        val entities = configDto.toKeyValueEntityList()
        keyValueStoreService.saveOrUpdateBatch(entities)
        return ResponseUtils.successResponse(selectConfig())
    }
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