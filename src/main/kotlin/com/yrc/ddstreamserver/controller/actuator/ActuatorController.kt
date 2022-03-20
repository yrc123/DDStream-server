package com.yrc.ddstreamserver.controller.actuator

import cn.dev33.satoken.annotation.SaCheckPermission
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.exception.common.EnumServerException
import com.yrc.ddstreamserver.pojo.actuator.CommonActuatorDto
import com.yrc.ddstreamserver.pojo.actuator.DetailActuatorDto
import com.yrc.ddstreamserver.pojo.actuator.HealthDto
import com.yrc.ddstreamserver.pojo.permission.PermissionName.CLIENT_READ
import com.yrc.ddstreamserver.service.feign.actuator.ActuatorService
import com.yrc.ddstreamserver.service.feign.actuator.ActuatorServiceFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Callable

@RestController
@RequestMapping("/api/v1")
class ActuatorController(
    private val actuatorServiceFactory: ActuatorServiceFactory,
    private val threadPoolTaskExecutor: ThreadPoolTaskExecutor,
) {
    @SaCheckPermission(CLIENT_READ)
    @GetMapping("/actuator/{clientId}/health")
    fun getHealth(@PathVariable clientId: String): ResponseDto<HealthDto> {
       return ResponseUtils.successResponse(
           actuatorServiceFactory
               .getServiceInstance(clientId)
               .getHealth()
       )
    }

    @SaCheckPermission(CLIENT_READ)
    @GetMapping("/actuator/{clientId}/metrics/process.cpu.usage")
    fun getProcessCpuUsage(@PathVariable clientId: String): ResponseDto<CommonActuatorDto> {
        return ResponseUtils.successResponse(
            actuatorServiceFactory.getServiceInstance(clientId)
                .getProcessCpuUsage()
        )
    }

    @SaCheckPermission(CLIENT_READ)
    @GetMapping("/actuator/{clientId}/metrics/process.uptime")
    fun getProcessUptime(@PathVariable clientId: String): ResponseDto<CommonActuatorDto> {
        return ResponseUtils.successResponse(
            actuatorServiceFactory.getServiceInstance(clientId)
                .getProcessUptime()
        )
    }

    @SaCheckPermission(CLIENT_READ)
    @GetMapping("/actuator/{clientId}/metrics/system.cpu.usage")
    fun getSystemCpuUsage(@PathVariable clientId: String): ResponseDto<CommonActuatorDto> {
        return ResponseUtils.successResponse(
            actuatorServiceFactory.getServiceInstance(clientId)
                .getSystemCpuUsage()
        )
    }

    @SaCheckPermission(CLIENT_READ)
    @GetMapping("/actuator/{clientId}/metrics/system.cpu.count")
    fun getSystemCpuCount(@PathVariable clientId: String): ResponseDto<CommonActuatorDto> {
        return ResponseUtils.successResponse(
            actuatorServiceFactory.getServiceInstance(clientId)
                .getSystemCpuCount()
        )
    }

    @SaCheckPermission(CLIENT_READ)
    @GetMapping("/actuator/{clientId}/metrics/jvm.buffer.memory.used")
    fun getJvmBufferMemoryUsed(@PathVariable clientId: String): ResponseDto<CommonActuatorDto> {
        return ResponseUtils.successResponse(
            actuatorServiceFactory.getServiceInstance(clientId)
                .getJvmBufferMemoryUsed()
        )
    }

    @SaCheckPermission(CLIENT_READ)
    @GetMapping("/actuator/{clientId}/metrics/jvm.memory.max")
    fun getJvmMemoryMax(@PathVariable clientId: String): ResponseDto<CommonActuatorDto> {
        return ResponseUtils.successResponse(
            actuatorServiceFactory.getServiceInstance(clientId)
                .getJvmMemoryMax()
        )
    }

    @SaCheckPermission(CLIENT_READ)
    @GetMapping("/actuator/{clientId}/metrics/jvm.memory.used")
    fun getJvmMemoryUsed(@PathVariable clientId: String): ResponseDto<CommonActuatorDto> {
        return ResponseUtils.successResponse(
            actuatorServiceFactory.getServiceInstance(clientId)
                .getJvmMemoryUsed()
        )
    }

    @SaCheckPermission(CLIENT_READ)
    @GetMapping("/actuator/{clientId}/logfile")
    fun getLogFile(@PathVariable clientId: String): ResponseDto<String> {
        return ResponseUtils.successResponse(
            actuatorServiceFactory.getServiceInstance(clientId)
                .getLogFile()
        )
    }

    @SaCheckPermission(CLIENT_READ)
    @GetMapping("/actuator/{clientId}")
    @Cacheable(value = ["default-3s"], key = "#clientId")
    fun getDetail(@PathVariable clientId: String): ResponseDto<DetailActuatorDto> {
        val serviceInstance = actuatorServiceFactory.getServiceInstance(clientId)
        val commonActuatorDtoFutures = listOf(
            ActuatorService::getProcessCpuUsage,
            ActuatorService::getSystemCpuUsage,
            ActuatorService::getJvmMemoryMax,
            ActuatorService::getJvmMemoryUsed,
            ActuatorService::getProcessUptime,
        ).map {
            threadPoolTaskExecutor.submit(
                Callable<CommonActuatorDto?> {
                    try {
                        it.invoke(serviceInstance)
                    } catch (e: Exception) {
                        throw EnumServerException.CAN_NOT_CONNECT_TO_CLIENT.build()
                    }
                }
            )
        }
        val healthDtoFuture = threadPoolTaskExecutor.submit(
            Callable<HealthDto?> {
                try {
                    ActuatorService::getHealth.invoke(serviceInstance)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        )
        return ResponseUtils.successResponse(
            DetailActuatorDto(
                healthDtoFuture.get(),
                commonActuatorDtoFutures[0].get(),
                commonActuatorDtoFutures[1].get(),
                commonActuatorDtoFutures[2].get(),
                commonActuatorDtoFutures[3].get(),
                commonActuatorDtoFutures[4].get(),
            )
        )
    }
}