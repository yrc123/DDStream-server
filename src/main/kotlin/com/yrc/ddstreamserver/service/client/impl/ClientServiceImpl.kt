package com.yrc.ddstreamserver.service.client.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yrc.ddstreamserver.dao.client.ClientMapper
import com.yrc.ddstreamserver.pojo.client.ClientEntity
import com.yrc.ddstreamserver.service.client.ClientService
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ClientServiceImpl(
    val discoveryClient: DiscoveryClient,
    @Value("\${dd-stream.client.application.name}") val applicationName: String,
) : ClientService, ServiceImpl<ClientMapper, ClientEntity>() {

    private var onlineClientMap = mapOf<String, ServiceInstance>()

    val ServiceInstance.id: String
        get() = "${this.host}:${this.port}"

    @Scheduled(cron = "*/10 * * * * *")
    fun updateClientList() {
        val instances = discoveryClient.getInstances(applicationName)
        onlineClientMap = instances.associateBy { it.id }

        val ids = instances
            .mapNotNull { it.id }
            .toSet()
        val idsInDb = baseMapper.selectBatchIds(ids)
            .map { it.id }
            .toSet()

        val clientDtos = instances.filterNotNull()
            .filterNot { idsInDb.contains(it.id) }
            .map {
                ClientEntity(it.id, it.host, it.port, it.id, "")
            }
        this.saveBatch(clientDtos)
    }

    override fun clientContains(id: String): Boolean {
        return onlineClientMap.containsKey(id)
    }

    override fun getClientInstance(id: String): ServiceInstance? {
        return onlineClientMap[id]
    }
}