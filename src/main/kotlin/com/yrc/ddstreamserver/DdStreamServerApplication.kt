package com.yrc.ddstreamserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DdStreamServerApplication

fun main(args: Array<String>) {
    runApplication<DdStreamServerApplication>(*args)
}
