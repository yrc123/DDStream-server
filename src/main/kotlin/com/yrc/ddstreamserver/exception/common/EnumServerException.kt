package com.yrc.ddstreamserver.exception.common

import com.yrc.common.exception.common.impl.SimpleException
import org.apache.http.HttpStatus

enum class EnumServerException(private val exception: SimpleException) {
    UNKNOWN_FEIGN_BAD_REQUEST_REASON(
        SimpleException(HttpStatus.SC_BAD_REQUEST,
        "unknow feign bad request")
    ),
    PATH_VARIABLE_NOT_EQUALS(
        SimpleException(HttpStatus.SC_BAD_REQUEST,
            "path variable not equals")
    ),
    NOT_FOUND(
        SimpleException(HttpStatus.SC_NOT_FOUND,
            "not found")
    ),
    ;
    fun build(): SimpleException {
        return exception
    }
}