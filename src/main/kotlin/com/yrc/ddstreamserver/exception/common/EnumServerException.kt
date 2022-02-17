package com.yrc.ddstreamserver.exception.common

import com.yrc.common.exception.common.impl.SimpleException
import org.apache.http.HttpStatus

enum class EnumServerException(private val exception: SimpleException) {
    UNKNOWN_FEIGN_BAD_REQUEST_REASON(
        SimpleException(HttpStatus.SC_BAD_REQUEST,
            "未知错误",
            "unknown feign bad request")
    ),
    PATH_VARIABLE_NOT_EQUALS(
        SimpleException(HttpStatus.SC_BAD_REQUEST,
            "url参数错误",
            "path variable not equals")
    ),
    DUPLICATE_KEY(
        SimpleException(HttpStatus.SC_BAD_REQUEST,
            "重复插入",
            "duplicate key"
        )
    ),
    NOT_FOUND(
        SimpleException(HttpStatus.SC_NOT_FOUND,
            "找不到资源",
            "not found")
    ),
    ;
    fun build(): SimpleException {
        return exception
    }
}