package com.yrc.ddstreamserver.pojo.actuator

data class HealthDto (
    val status: String? = null,
    val components: Components? = null
)

data class  Components(
    val consul: Consul? = null,
    val db: DB? = null,
    val discoveryComposite: DiscoveryComposite? = null,
    val diskSpace: DiskSpace? = null,
    val ping: Ping? = null,
    val refreshScope: Ping? = null
)

data class Consul (
    val status: String? = null,
    val details: ConsulDetails? = null
)

data class ConsulDetails (
    val leader: String? = null,
    val services: Services? = null
)

data class Services (
    val ddStreamClient: List<Any?> ? =null,
    val consul: List<Any?>? = null
)

data class DB (
    val status: String? = null,
    val details: DBDetails? = null
)

data class DBDetails (
    val database: String? = null,
    val validationQuery: String? = null
)

data class DiscoveryComposite (
    val status: String? = null,
    val components: DiscoveryCompositeComponents? = null
)

data class DiscoveryCompositeComponents (
    val discoveryClient: DiscoveryClient? = null
)

data class DiscoveryClient (
    val status: String? = null,
    val details: DiscoveryClientDetails? = null
)

data class DiscoveryClientDetails (
    val services: List<String>? = null
)

data class DiskSpace (
    val status: String? = null,
    val details: DiskSpaceDetails? = null
)

data class DiskSpaceDetails (
    val total: Long? = null,
    val free: Long? = null,
    val threshold: Long? = null,
    val exists: Boolean? = null
)

data class Ping (
    val status: String? = null
)
