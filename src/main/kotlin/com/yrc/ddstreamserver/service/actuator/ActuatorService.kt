package com.yrc.ddstreamserver.service.actuator

import org.springframework.web.bind.annotation.GetMapping

interface ActuatorService {
    @GetMapping("/actuator/health")
    fun getHealth()

    @GetMapping("/actuator/metrics/process.cpu.usage")
    fun getProcessCpuUsage()

    @GetMapping("/actuator/metrics/process.uptime")
    fun getProcessUptime()

    @GetMapping("/actuator/metrics/system.cpu.usage")
    fun getSystemCpuUsage()

    @GetMapping("/actuator/metrics/system.cpu.count")
    fun getSystemCpuCount()

    @GetMapping("/actuator/metrics/jvm.buffer.memory.used")
    fun getJvmBufferMemoryUsed()

    @GetMapping("/actuator/metrics/jvm.memory.max")
    fun getJvmMemoryMax()

    @GetMapping("/actuator/metrics/jvm.memory.used")
    fun getJvmMemoryUsed()

    @GetMapping("/actuator/logfile")
    fun getLogFile(): String

}