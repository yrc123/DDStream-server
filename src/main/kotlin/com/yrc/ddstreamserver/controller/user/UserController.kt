package com.yrc.ddstreamserver.controller.user

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.secure.SaSecureUtil
import cn.dev33.satoken.stp.StpUtil
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.controller.common.ControllerUtils
import com.yrc.ddstreamserver.exception.common.EnumServerException
import com.yrc.ddstreamserver.pojo.user.UserDto
import com.yrc.ddstreamserver.pojo.user.UserEntity
import com.yrc.ddstreamserver.service.user.UserService
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.valiktor.functions.hasSize
import org.valiktor.functions.isEmail
import org.valiktor.validate

@Controller
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    @Value("dd-stream.server.password-salt")
    private val salt: String
) {
    @SaCheckLogin
    @GetMapping("/{username}")
    fun getUserInfo(@PathVariable username: String): ResponseDto<UserDto> {
        val userList = userService.listByUsernames(listOf(username))
        if (userList.isEmpty()) {
            throw EnumServerException.NOT_FOUND.build()
        } else {
            val userDto = UserDto()
            BeanUtils.copyProperties(userList.first(), userDto)
            userDto.password = null
            return ResponseUtils.successResponse(userDto)
        }
    }

    @PostMapping("/{username}")
    fun register(@PathVariable username:String,
        @RequestBody userDto: UserDto): ResponseDto<UserDto> {
        validate(userDto) {
            validate(UserDto::username)
                .hasSize(UserDto.USERNAME_MIN, UserDto.USERNAME_MAX)
            validate(UserDto::password)
                .hasSize(0, UserDto.PASSWORD_MAX)
            validate(UserDto::nickname)
                .hasSize(UserDto.NICKNAME_MIN, UserDto.NICKNAME_MAX)
            if (userDto.email != null) {
                validate(UserDto::email).isEmail()
            }
        }
        ControllerUtils.checkPathVariable(username, userDto.username)
        val userEntity = UserEntity()
        BeanUtils.copyProperties(userDto, userEntity)
        userEntity.password = SaSecureUtil.md5BySalt(userDto.password, salt)
        userService.save(userEntity)
        StpUtil.login(userEntity.id, true)
        return ResponseUtils.successResponse(userDto)
    }
}