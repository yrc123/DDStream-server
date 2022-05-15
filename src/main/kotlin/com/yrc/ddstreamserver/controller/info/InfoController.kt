package com.yrc.ddstreamserver.controller.info

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.annotation.SaCheckSafe
import cn.dev33.satoken.stp.StpUtil
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.ddstreamserver.controller.user.UserController
import com.yrc.ddstreamserver.pojo.user.UserDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class InfoController(
    private val userController: UserController
) {
    @SaCheckLogin
    @GetMapping("/info")
    fun getUserInfo(): ResponseDto<UserDto> {
        val userId = StpUtil.getLoginIdAsString()
        return userController.getUserInfo(userId)
    }

    @SaCheckSafe
    @PatchMapping("/info")
    fun updateUser(@RequestBody userDto: UserDto): ResponseDto<UserDto> {
        UserDto.updateValidator.invoke(userDto)
        val userId = StpUtil.getLoginIdAsString()
        return userController.updateUser(userId, userDto)
    }
}