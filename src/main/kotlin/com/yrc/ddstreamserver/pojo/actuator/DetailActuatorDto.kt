package com.yrc.ddstreamserver.pojo.actuator

data class DetailActuatorDto(
    var healthDto: HealthDto? = null,
    var processCpuUsage: CommonActuatorDto? = null,
    var systemCpuUsage: CommonActuatorDto? = null,
    var jvmMemoryMax: CommonActuatorDto? = null,
    var jvmMemoryUsed: CommonActuatorDto? = null,
    var processUptime: CommonActuatorDto? = null,
)
