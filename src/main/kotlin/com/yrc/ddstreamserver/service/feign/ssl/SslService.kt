package com.yrc.ddstreamclient.controller.ssl

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping("/api/client")
interface SslService {
    @GetMapping("/ssl")
    fun updateKeyManager()
}
