package com.yrc.ddstreamserver.pojo.actuator

data class CommonActuatorDto (
    val name: String? = null,
    val description: String? =null,
    val baseUnit: String? = null,
    val measurements: List<Measurement>? = null,
    val availableTags: List<AvailableTag>? = null
)

data class Measurement (
    val statistic: String? = null,
    val value: Double? = null
)

data class AvailableTag (
    val tag: String? = null,
    val values: List<String>? = null
)
