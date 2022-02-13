package com.yrc.ddstreamserver.controller.auth

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.secure.SaSecureUtil
import cn.dev33.satoken.stp.StpUtil
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.controller.common.ControllerUtils
import com.yrc.ddstreamserver.pojo.auth.AuthDto
import com.yrc.ddstreamserver.pojo.user.UserDto
import com.yrc.ddstreamserver.service.user.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.valiktor.functions.hasSize
import org.valiktor.validate

@Controller
@RequestMapping("/api/v1/users")
class AuthController(
    private val userService: UserService,
    @Value("dd-stream.server.password-salt")
    private val salt: String
) {

    @PostMapping("/{username}:login")
    fun login(@PathVariable username: String, @RequestBody authDto: AuthDto): ResponseDto<String> {
        validate(authDto) {
            validate(AuthDto::username)
                .hasSize(UserDto.USERNAME_MIN, UserDto.USERNAME_MAX)
            validate(AuthDto::password)
                .hasSize(0, UserDto.PASSWORD_MAX)
        }
        ControllerUtils.checkPathVariable(username, authDto.username)
        val userList = userService.listByUsernames(listOf(username))
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
    @GetMapping("/{username}:logout")
    fun logout(@PathVariable username: String): ResponseDto<String> {
        StpUtil.logout()
        return ResponseUtils.successStringResponse()
    }
}