package com.yrc.ddstreamserver.pojo.common

enum class ApplicationConfiguration(val key: String) {
    JWT_ENCODE_PRIVATE_KEY("JWT_ENCODE_PRIVATE_KEY"),
    JWT_ENCODE_PUBLIC_KEY("JWT_ENCODE_PUBLIC_KEY"), ;

    override fun toString(): String {
        return key
    }

}