package com.yrc.ddstreamserver.pojo.config

enum class ApplicationConfiguration(val key: String) {
    JWT_PRIVATE_KEY("JWT_PRIVATE_KEY"),
    JWT_PUBLIC_KEY("JWT_PUBLIC_KEY"),
    OPEN_REGISTER("OPEN_REGISTER"),
    INIT("INIT"),
    ;

    override fun toString(): String {
        return key
    }

}