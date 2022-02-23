package com.yrc.ddstreamserver.controller.user

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.annotation.SaCheckPermission
import cn.dev33.satoken.secure.SaSecureUtil
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.PageUtils.converterResultPage
import com.yrc.common.utils.PageUtils.converterSearchPage
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.controller.common.ControllerUtils
import com.yrc.ddstreamserver.exception.common.EnumServerException
import com.yrc.ddstreamserver.pojo.permission.PermissionName.USER_READ
import com.yrc.ddstreamserver.pojo.permission.PermissionName.USER_WRITE
import com.yrc.ddstreamserver.pojo.user.UserDto
import com.yrc.ddstreamserver.pojo.user.UserEntity
import com.yrc.ddstreamserver.service.user.UserService
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserController(
    private val userService: UserService,
    @Value("dd-stream.server.password-salt")
    private val salt: String
) {
    @SaCheckLogin
    @GetMapping("/users/{userId}")
    fun getUserInfo(@PathVariable userId: String): ResponseDto<UserDto> {
        val userList = userService.listByIds(listOf(userId))
        if (userList.isEmpty()) {
            throw EnumServerException.NOT_FOUND.build()
        } else {
            val userDto = UserDto()
            BeanUtils.copyProperties(userList.first(), userDto)
            userDto.password = null
            return ResponseUtils.successResponse(userDto)
        }
    }

    @SaCheckPermission(USER_READ)
    @GetMapping("/users")
    fun listUsers(page: PageDTO<UserDto>): ResponseDto<PageDTO<UserDto>> {
        val searchPage = page.converterSearchPage<UserDto, UserEntity>()
        val resultPage = userService.page(searchPage)
            .converterResultPage(UserDto::class, ControllerUtils::defaultPageConverterMethod)
        return ResponseUtils.successResponse(resultPage)
    }

    @SaCheckPermission(USER_WRITE)
    @DeleteMapping("/users/{userId}")
    fun deleteUser(@PathVariable userId: String): ResponseDto<String> {
        userService.removeById(userId)
        return ResponseUtils.successStringResponse()
    }

    @SaCheckPermission(USER_WRITE)
    @PatchMapping("/users/{userId}")
    fun updateUser(@PathVariable userId: String, @RequestBody userDto: UserDto): ResponseDto<UserDto> {
        UserDto.updateValidator.invoke(userDto)
        ControllerUtils.checkPathVariable(userId, userDto.id)
        userDto.password = SaSecureUtil.md5BySalt(userDto.password, salt)
        val userEntities = userService.listByIds(listOf(userDto.id))
        if (userEntities.isNotEmpty()) {
            val resultDto = ControllerUtils.updateAndReturnDto(
                userService,
                userDto,
                userEntities.first(),
                UserDto::class
            )
            resultDto.password = null
            return ResponseUtils.successResponse(resultDto)
        } else {
            throw EnumServerException.NOT_FOUND.build()
        }
    }

}