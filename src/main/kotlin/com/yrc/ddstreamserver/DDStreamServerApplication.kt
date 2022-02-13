package com.yrc.ddstreamserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
//@EnableFeignClients
@EnableScheduling
@EnableCaching
class DDStreamServerApplication

fun main(args: Array<String>) {
    runApplication<DDStreamServerApplication>(*args)
}
