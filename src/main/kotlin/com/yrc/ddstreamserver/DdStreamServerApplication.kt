package com.yrc.ddstreamserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class DdStreamServerApplication

fun main(args: Array<String>) {
    runApplication<DdStreamServerApplication>(*args)
}
