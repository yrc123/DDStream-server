package com.yrc.ddstreamserver.service.feign.actuator

import com.yrc.ddstreamserver.pojo.actuator.CommonActuatorDto
import com.yrc.ddstreamserver.pojo.actuator.HealthDto
import org.springframework.web.bind.annotation.GetMapping

interface ActuatorService {
    @GetMapping("/actuator/health")
    fun getHealth(): HealthDto

    @GetMapping("/actuator/metrics/process.cpu.usage")
    fun getProcessCpuUsage(): CommonActuatorDto

    @GetMapping("/actuator/metrics/process.uptime")
    fun getProcessUptime(): CommonActuatorDto

    @GetMapping("/actuator/metrics/system.cpu.usage")
    fun getSystemCpuUsage(): CommonActuatorDto

    @GetMapping("/actuator/metrics/system.cpu.count")
    fun getSystemCpuCount(): CommonActuatorDto

    @GetMapping("/actuator/metrics/jvm.buffer.memory.used")
    fun getJvmBufferMemoryUsed(): CommonActuatorDto

    @GetMapping("/actuator/metrics/jvm.memory.max")
    fun getJvmMemoryMax(): CommonActuatorDto

    @GetMapping("/actuator/metrics/jvm.memory.used")
    fun getJvmMemoryUsed(): CommonActuatorDto

    @GetMapping("/actuator/logfile")
    fun getLogFile(): String

//    fun getDetail(threadPoolTaskExecutor: ThreadPoolTaskExecutor): DetailActuatorDto {
//        val commonActuatorDtoFutures = listOf(
//            this::getProcessCpuUsage,
//            this::getSystemCpuUsage,
//            this::getJvmMemoryMax,
//            this::getJvmMemoryUsed,
//            this::getProcessUptime,
//        ).map {
//            threadPoolTaskExecutor.submit(
//                Callable<CommonActuatorDto?> {
//                    try {
//                        it.invoke()
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                        null
//                    }
//                }
//            )
//        }
//        val healthdtoFuture = threadPoolTaskExecutor.submit(
//            Callable<HealthDto?> {
//                try {
//                    this.getHealth()
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    null
//                }
//            }
//        )
//        return DetailActuatorDto(
//            healthdtoFuture.get(),
//            commonActuatorDtoFutures[0].get(),
//            commonActuatorDtoFutures[1].get(),
//            commonActuatorDtoFutures[2].get(),
//            commonActuatorDtoFutures[3].get(),
//            commonActuatorDtoFutures[4].get(),
//        )
//    }
}