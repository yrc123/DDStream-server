package com.yrc.ddstreamserver.controller.auth

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.secure.SaSecureUtil
import cn.dev33.satoken.stp.StpUtil
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.controller.common.ControllerUtils
import com.yrc.ddstreamserver.pojo.auth.AuthDto
import com.yrc.ddstreamserver.pojo.user.UserDto
import com.yrc.ddstreamserver.pojo.user.UserEntity
import com.yrc.ddstreamserver.service.user.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import org.valiktor.functions.hasSize
import org.valiktor.functions.isEmail
import org.valiktor.validate

@RestController
@RequestMapping("/api/v1")
class AuthController(
    private val userService: UserService,
    @Value("dd-stream.server.password-salt")
    private val salt: String
) {

    @PostMapping("/auth/login")
    fun login(@RequestBody authDto: AuthDto): ResponseDto<String> {
        validate(authDto) {
            validate(AuthDto::username)
                .hasSize(UserDto.USERNAME_MIN, UserDto.USERNAME_MAX)
            validate(AuthDto::password)
                .hasSize(0, UserDto.PASSWORD_MAX)
        }
        val userList = userService.listByUsernames(listOf(authDto.username!!))
        return if (userList.size != 1) {
             ResponseUtils.failStringResponse("user does not exist")
        } else {
            userList.first().let {
                val md5Password = SaSecureUtil.md5BySalt(authDto.password, salt)
                if (it.password == md5Password) {
                    StpUtil.login(it.id, authDto.rememberMe ?: false)
                    ResponseUtils.successStringResponse()
                } else {
                    ResponseUtils.failStringResponse()
                }
            }
        }
    }

    @PostMapping("/auth/register")
    fun register(@RequestBody userDto: UserDto): ResponseDto<UserDto> {
        validate(userDto) {
            validate(UserDto::username)
                .hasSize(UserDto.USERNAME_MIN, UserDto.USERNAME_MAX)
            validate(UserDto::password)
                .hasSize(1, UserDto.PASSWORD_MAX)
            validate(UserDto::nickname)
                .hasSize(UserDto.NICKNAME_MIN, UserDto.NICKNAME_MAX)
            if (userDto.email != null) {
                validate(UserDto::email).isEmail()
            }
        }
        val resultDto = ControllerUtils.saveAndReturnDto(
            userService,
            userDto,
            UserDto::class,
            UserEntity::class
        )
        StpUtil.login(resultDto.id, true)
        return ResponseUtils.successResponse(resultDto)
    }

    @SaCheckLogin
    @GetMapping("/auth/{username}:logout")
    fun logout(@PathVariable username: String): ResponseDto<String> {
        StpUtil.logout()
        return ResponseUtils.successStringResponse()
    }
}