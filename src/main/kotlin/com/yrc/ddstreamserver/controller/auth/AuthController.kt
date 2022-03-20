package com.yrc.ddstreamserver.controller.auth

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.secure.SaSecureUtil
import cn.dev33.satoken.stp.StpUtil
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.controller.common.ControllerUtils
import com.yrc.ddstreamserver.exception.common.EnumServerException
import com.yrc.ddstreamserver.pojo.auth.AuthDto
import com.yrc.ddstreamserver.pojo.config.ApplicationConfiguration
import com.yrc.ddstreamserver.pojo.user.UserDto
import com.yrc.ddstreamserver.pojo.user.UserEntity
import com.yrc.ddstreamserver.service.keyvaluestore.KeyValueStoreService
import com.yrc.ddstreamserver.service.user.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class AuthController(
    private val userService: UserService,
    private val keyValueStoreService: KeyValueStoreService,
    @Value("dd-stream.server.password-salt")
    private val salt: String,
) {

    @PostMapping("/auth/register")
    fun register(@RequestBody userDto: UserDto): ResponseDto<String> {
        val openRegister = keyValueStoreService
            .getById(ApplicationConfiguration.OPEN_REGISTER.toString())
            .value
            ?.toBoolean() ?: false
        if (!openRegister) {
            throw EnumServerException.NOT_OPEN_REGISTER.build()
        }
        UserDto.commonValidator.invoke(userDto)
        userDto.password = SaSecureUtil.md5BySalt(userDto.password, salt)
        val resultDto = ControllerUtils.saveAndReturnDto(
            userService,
            userDto,
            UserDto::class,
            UserEntity::class
        )
        StpUtil.login(resultDto.id, true)
        return ResponseUtils.successResponse(StpUtil.getTokenValue())
    }

    @PostMapping("/auth/login")
    fun login(@RequestBody authDto: AuthDto): ResponseDto<String> {
        AuthDto.commonValidator.invoke(authDto)
        val userList = userService.listByUsernames(listOf(authDto.username!!))
        return if (userList.size != 1) {
            ResponseUtils.failStringResponse("用户不存在")
        } else {
            userList.first().let {
                val md5Password = SaSecureUtil.md5BySalt(authDto.password, salt)
                if (it.password == md5Password) {
                    StpUtil.login(it.id, authDto.rememberMe ?: false)
                    ResponseUtils.successResponse(StpUtil.getTokenValue())
                } else {
                    ResponseUtils.failStringResponse("密码错误")
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
                    StpUtil.openSafe(600)
                    ResponseUtils.successStringResponse()
                } else {
                    ResponseUtils.failStringResponse()
                }
            }
        }
    }

    @GetMapping("/auth/logout")
    fun logout(): ResponseDto<String> {
        StpUtil.logout()
        return ResponseUtils.successStringResponse()
    }

}