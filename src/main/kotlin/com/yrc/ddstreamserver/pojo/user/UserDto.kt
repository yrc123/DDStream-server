package com.yrc.ddstreamserver.pojo.user

data class UserDto(
    var id: String? = null,
    var username: String? = null,
    var password: String? = null,
    var nickname: String? = null,
    var email: String? = null
) {
    companion object {
        val USERNAME_MIN = 1
        val USERNAME_MAX = 64
        val PASSWORD_MAX = 128
        val NICKNAME_MIN = 1
        val NICKNAME_MAX = 128
        val EMAIL_MAX = 64
    }
}
