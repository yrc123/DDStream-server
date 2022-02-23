package com.yrc.ddstreamserver.pojo.auth

import com.yrc.ddstreamserver.pojo.user.UserDto
import org.valiktor.functions.hasSize
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class AuthDto(
    val username: String? = null,
    val password: String? = null,
    val rememberMe: Boolean? = null,
) {
    companion object {
        val commonValidator = { authDto: AuthDto ->
            validate(authDto) {
                validate(AuthDto::username)
                    .isNotNull()
                    .hasSize(UserDto.USERNAME_MIN, UserDto.USERNAME_MAX)
                validate(AuthDto::password)
                    .isNotNull()
                    .hasSize(UserDto.PASSWORD_MIN, UserDto.PASSWORD_MAX)
            }
        }
    }
}
