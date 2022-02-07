package com.yrc.ddstreamserver.service.client.impl

import com.yrc.ddstreamserver.service.client.ClientService
import org.springframework.stereotype.Service

@Service
class ClientServiceImpl : ClientService{
//    fun getClientList(): List<ClientEntity> {
//        val streamService = Feign.builder()
//            .client(client)
//            .contract(SpringMvcContract())
//            .target(
//                StreamService::class.java,
//                discoveryClient.getInstances("stream-client").get(0).uri.toString()
//            )
//    }
}