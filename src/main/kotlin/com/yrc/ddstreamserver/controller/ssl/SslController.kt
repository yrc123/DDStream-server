package com.yrc.ddstreamserver.controller.ssl

import com.yrc.ddstreamserver.service.feign.ssl.SslServiceFacotry
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class SslController(
    private val sslServiceFacotry: SslServiceFacotry,
) {
}