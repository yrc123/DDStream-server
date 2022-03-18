package com.yrc.ddstreamserver.controller.config

import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.pojo.config.ApplicationConfiguration
import com.yrc.ddstreamserver.pojo.config.ConfigDto
import com.yrc.ddstreamserver.pojo.keyvaluestore.KeyValueEntity
import com.yrc.ddstreamserver.service.keyvaluestore.KeyValueStoreService
import org.apache.commons.beanutils.BeanUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/api")
class ConfigController(
    private val keyValueStoreService: KeyValueStoreService
) {
    @GetMapping("/config")
    fun getConfig(): ResponseDto<ConfigDto> {
        return ResponseUtils.successResponse(selectConfig())
    }
    @PatchMapping("/config")
    fun updateConfig(configDto: ConfigDto): ResponseDto<ConfigDto> {
        val entities = BeanUtils.describe(configDto)
            .filter { it.key != null && it.value != null }
            .mapNotNull { KeyValueEntity(it.key, it.value) }
        keyValueStoreService.saveOrUpdateBatch(entities)
        return ResponseUtils.successResponse(selectConfig())
    }
    private fun selectConfig(): ConfigDto {
        val configMap = keyValueStoreService.listByIds(ApplicationConfiguration.values()
            .map { it.toString() })
            .mapNotNull { it.key to it.value }
            .toMap()
        val resultDto = ConfigDto()
        BeanUtils.populate(resultDto, configMap)
        return resultDto
    }
}