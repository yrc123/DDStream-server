package com.yrc.ddstreamserver.service.feign.ssl

import com.yrc.ddstreamclient.controller.ssl.SslService
import com.yrc.ddstreamserver.service.feign.AbstractFeignClientFactory
import org.springframework.stereotype.Component

@Component
class SslServiceFacotry : AbstractFeignClientFactory<SslService>(SslService::class){
}