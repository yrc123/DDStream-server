package com.yrc.ddstreamserver.pojo.auth

data class AuthDto(
    val username: String? = null,
    val password: String? = null,
    val rememberMe: Boolean? = null,
) {
}
