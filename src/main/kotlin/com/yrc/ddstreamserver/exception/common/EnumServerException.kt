package com.yrc.ddstreamserver.exception.common

import com.yrc.common.exception.common.impl.SimpleException
import org.apache.http.HttpStatus

enum class EnumServerException(private val exception: SimpleException) {
    UNKNOWN_FEIGN_BAD_REQUEST_REASON(
        SimpleException(
            HttpStatus.SC_BAD_REQUEST,
            "未知错误",
            "unknown feign bad request"
        )
    ),
    PATH_VARIABLE_NOT_EQUALS(
        SimpleException(
            HttpStatus.SC_BAD_REQUEST,
            "url参数错误",
            "path variable not equals"
        )
    ),
    DUPLICATE_KEY(
        SimpleException(
            HttpStatus.SC_BAD_REQUEST,
            "重复插入",
            "duplicate key"
        )
    ),
    NOT_FOUND(
        SimpleException(
            HttpStatus.SC_NOT_FOUND,
            "找不到资源",
            "not found"
        )
    ),
    NOT_LOGIN(
        SimpleException(
            HttpStatus.SC_FORBIDDEN,
            "权限不足",
            "forbidden"
        )
    ),
    CAN_NOT_CONNECT_TO_CLIENT(
        SimpleException(
            HttpStatus.SC_INTERNAL_SERVER_ERROR,
            "无法连接到推流服务器",
            "can not connect to client"
        )
    ),
    PERMISSION_NOT_CONTAINS(
        SimpleException(
            HttpStatus.SC_BAD_REQUEST,
            "权限不存在",
            "permission not exist"
        )
    ),
    JWT_PRIVATE_KEY_NOT_CONTAINS(
        SimpleException(
            HttpStatus.SC_INTERNAL_SERVER_ERROR,
            "jwt密钥不存在",
            "jwt private key not contains"
        )
    ),
    NOT_OPEN_REGISTER(
        SimpleException(
            HttpStatus.SC_BAD_REQUEST,
            "未开放注册",
            "not open register"
        )
    ),
    NOT_SUPPORT_DECODE_JWS(
        SimpleException(HttpStatus.SC_BAD_REQUEST,
            "not support decode jws")
    ),
    ;

    fun build(): SimpleException {
        return exception
    }
}