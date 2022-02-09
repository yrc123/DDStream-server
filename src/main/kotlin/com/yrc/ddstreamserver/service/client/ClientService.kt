package com.yrc.ddstreamserver.service.client

import com.baomidou.mybatisplus.extension.service.IService
import com.yrc.ddstreamserver.pojo.client.ClientEntity
import org.springframework.cloud.client.ServiceInstance

interface ClientService : IService<ClientEntity>{
    fun clientContains(id: String): Boolean
    fun getClientInstance(id: String): ServiceInstance?
}