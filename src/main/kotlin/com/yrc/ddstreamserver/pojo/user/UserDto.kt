package com.yrc.ddstreamserver.pojo.user

import org.valiktor.functions.hasSize
import org.valiktor.functions.isEmail
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNull
import org.valiktor.validate

data class UserDto(
    var id: String? = null,
    var username: String? = null,
    var password: String? = null,
    var nickname: String? = null,
    var email: String? = null
) {
    companion object {
        const val ID_MAX = 64
        const val ID_MIN = 1
        const val USERNAME_MAX = 64
        const val USERNAME_MIN = 1
        const val PASSWORD_MAX = 128
        const val PASSWORD_MIN = 1
        const val NICKNAME_MAX = 128
        const val NICKNAME_MIN = 1
        const val EMAIL_MAX = 64
        val commonValidator = { userDto: UserDto ->
            validate(userDto) {
                validate(UserDto::username)
                    .isNotNull()
                    .hasSize(USERNAME_MIN, USERNAME_MAX)
                validate(UserDto::password)
                    .isNotNull()
                    .hasSize(PASSWORD_MIN, PASSWORD_MAX)
                validate(UserDto::nickname)
                    .isNotNull()
                    .hasSize(NICKNAME_MIN, NICKNAME_MAX)
                if (userDto.email != null) {
                    validate(UserDto::email).isEmail()
                }
            }
        }
        val updateValidator = { userDto: UserDto ->
            validate(userDto) {
                validate(UserDto::id)
                    .isNull()
                validate(UserDto::username)
                    .hasSize(USERNAME_MIN, USERNAME_MAX)
                validate(UserDto::password)
                    .hasSize(PASSWORD_MIN, PASSWORD_MAX)
                validate(UserDto::nickname)
                    .hasSize(NICKNAME_MIN, NICKNAME_MAX)
                if (userDto.email != null) {
                    validate(UserDto::email).isEmail()
                }
            }
        }
    }
}
