package com.yrc.ddstreamserver.controller.user

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.annotation.SaCheckPermission
import cn.dev33.satoken.secure.SaSecureUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.PageUtils.converterResultPage
import com.yrc.common.utils.PageUtils.converterSearchPage
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.controller.common.ControllerUtils
import com.yrc.ddstreamserver.exception.common.EnumServerException
import com.yrc.ddstreamserver.pojo.common.SearchPageDto
import com.yrc.ddstreamserver.pojo.permission.PermissionName.USER_READ
import com.yrc.ddstreamserver.pojo.permission.PermissionName.USER_WRITE
import com.yrc.ddstreamserver.pojo.user.UserDto
import com.yrc.ddstreamserver.pojo.user.UserEntity
import com.yrc.ddstreamserver.service.role.RoleService
import com.yrc.ddstreamserver.service.user.UserService
import com.yrc.ddstreamserver.service.userrole.UserRoleService
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserController(
    private val userService: UserService,
    private val userRoleService: UserRoleService,
    private val roleService: RoleService,
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
            val userRoleMap = userRoleService.listRolesByUserIds(listOf(userDto.id!!))
            userDto.roleList = userRoleMap[userDto.id] ?: listOf()
            return ResponseUtils.successResponse(userDto)
        }
    }

    @SaCheckPermission(USER_WRITE)
    @PostMapping("/users")
    fun insertUser(@RequestBody userDto: UserDto): ResponseDto<UserDto> {
        UserDto.commonValidator.invoke(userDto)
        val saveDto = ControllerUtils.saveAndReturnDto(
            userService,
            userDto,
            UserDto::class,
            UserEntity::class
        )
        userRoleService.saveRolesByUserId(saveDto.id!!, userDto.roleList!!)
        return ResponseUtils.successResponse(saveDto)
    }

    @SaCheckPermission(USER_READ)
    @PostMapping("/users:search")
    fun listUsers(@RequestBody searchPageDto: SearchPageDto<UserDto>): ResponseDto<PageDTO<UserDto>> {
        SearchPageDto.commonValidator.invoke(searchPageDto)
        val searchPage = searchPageDto.page!!.converterSearchPage<UserDto, UserEntity>()
        val queryWrapper = QueryWrapper<UserEntity>()
        searchPageDto.searchMap!!.forEach {
            queryWrapper.like(it.key.isNotBlank() && it.value.isNotBlank(), it.key, it.value)
        }
        val resultPage = userService.page(searchPage, queryWrapper)
            .converterResultPage(UserDto::class, ControllerUtils::defaultPageConverterMethod)
        val userIds = resultPage.records.mapNotNull { it.id }
        val userRoleMap = userRoleService.listRolesByUserIds(userIds)
        resultPage.records.forEach {
            it.password = null
            it.roleList = userRoleMap[it.id] ?: listOf()
        }
        return ResponseUtils.successResponse(resultPage)
    }

    @SaCheckPermission(USER_WRITE)
    @DeleteMapping("/users/{userId}")
    fun deleteUser(@PathVariable userId: String): ResponseDto<String> {
        userService.removeById(userId)
        return ResponseUtils.successStringResponse()
    }

    @SaCheckPermission(USER_WRITE)
    @PostMapping("/users:delete")
    fun deleteUsers(@RequestBody userIds: List<String?>): ResponseDto<String> {
        userService.removeBatchByIds(userIds.filterNotNull())
        return ResponseUtils.successStringResponse()
    }

    @SaCheckPermission(USER_WRITE)
    @PatchMapping("/users/{userId}")
    fun updateUser(@PathVariable userId: String, @RequestBody userDto: UserDto): ResponseDto<UserDto> {
        UserDto.updateValidator.invoke(userDto)
        ControllerUtils.checkPathVariable(userId, userDto.id)
        if (!userDto.password.isNullOrBlank()) {
            userDto.password = SaSecureUtil.md5BySalt(userDto.password, salt)
        }
        userDto.username = null
        val userEntities = userService.listByIds(listOf(userDto.id))
        if (userEntities.isNotEmpty()) {
            //??????user
            val resultDto = ControllerUtils.updateAndReturnDto(
                userService,
                userDto,
                userEntities.first(),
                UserDto::class
            )
            //??????role
            val userRoleMap = userRoleService.listRolesByUserIds(listOf(resultDto.id!!))
            val rolesInDb = userRoleMap[resultDto.id]
                ?.toSet()
                ?: setOf()
            val rolesInMem = userDto.roleList
                ?.toSet()
                ?: setOf()
            val rolesSave = rolesInMem - rolesInDb
            val rolesRemove = rolesInDb - rolesInMem
            userRoleService.removeRolesByUserId(resultDto.id!!, rolesRemove)
            userRoleService.saveRolesByUserId(resultDto.id!!, rolesSave)
            //??????
            resultDto.password = null
            resultDto.roleList = userRoleService.listRolesByUserIds(listOf(resultDto.id!!))[resultDto.id] ?: listOf()
            return ResponseUtils.successResponse(resultDto)
        } else {
            throw EnumServerException.NOT_FOUND.build()
        }
    }

}