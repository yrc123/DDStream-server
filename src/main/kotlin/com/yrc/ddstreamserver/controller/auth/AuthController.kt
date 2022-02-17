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

@RestController
@RequestMapping("/api/v1")
class AuthController(
    private val userService: UserService,
    @Value("dd-stream.server.password-salt")
    private val salt: String
) {

    @PostMapping("/auth/register")
    fun register(@RequestBody userDto: UserDto): ResponseDto<UserDto> {
        UserDto.commonValidator.invoke(userDto)
        userDto.password = SaSecureUtil.md5BySalt(userDto.password, salt)
        val resultDto = ControllerUtils.saveAndReturnDto(
            userService,
            userDto,
            UserDto::class,
            UserEntity::class
        )
        StpUtil.login(resultDto.id, true)
        return ResponseUtils.successResponse(resultDto)
    }

    @PostMapping("/auth/login")
    fun login(@RequestBody authDto: AuthDto): ResponseDto<String> {
        AuthDto.commonValidator.invoke(authDto)
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

    @SaCheckLogin
    @PostMapping("/auth/safe")
    fun safe(@RequestBody authDto: AuthDto): ResponseDto<String> {
        val userList = userService.listByIds(listOf(StpUtil.getLoginIdAsString()))
        return if (userList.size != 1) {
            ResponseUtils.failStringResponse("user does not exist")
        } else {
            userList.first().let {
                val md5Password = SaSecureUtil.md5BySalt(authDto.password, salt)
                if (it.password == md5Password) {
                    StpUtil.openSafe(120)
                    ResponseUtils.successStringResponse()
                } else {
                    ResponseUtils.failStringResponse()
                }
            }
        }
    }

    @SaCheckLogin
    @GetMapping("/auth/logout")
    fun logout(): ResponseDto<String> {
        StpUtil.logout()
        return ResponseUtils.successStringResponse()
    }

}