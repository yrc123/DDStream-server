package com.yrc.ddstreamserver.controller.userrole

import cn.dev33.satoken.annotation.SaCheckPermission
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.controller.common.ControllerUtils
import com.yrc.ddstreamserver.pojo.permission.PermissionName.USER_ROLE_READ
import com.yrc.ddstreamserver.pojo.permission.PermissionName.USER_ROLE_WRITE
import com.yrc.ddstreamserver.pojo.userrole.UserRoleDto
import com.yrc.ddstreamserver.pojo.userrole.UserRoleEntity
import com.yrc.ddstreamserver.service.userrole.UserRoleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserRoleController(
    private val userRoleService: UserRoleService
) {
    @SaCheckPermission(USER_ROLE_READ)
    @GetMapping("/users/{userId}/roles")
    fun listRolesByUserId(@PathVariable userId: String): ResponseDto<UserRoleDto> {
        val wrapper = KtQueryWrapper(UserRoleEntity::class.java)
            .`in`(UserRoleEntity::userId, listOf(userId))
        val roleIdList = userRoleService.list(wrapper)
            .mapNotNull { it.roleId }
        return ResponseUtils
            .successResponse(UserRoleDto(userId, roleIdList))
    }

    @SaCheckPermission(USER_ROLE_WRITE)
    @PostMapping("/users/{userId}/roles")
    fun updateRoles(@PathVariable userId: String,
                          @RequestBody userRoleDto: UserRoleDto
    ): ResponseDto<UserRoleDto> {
        ControllerUtils.checkPathVariable(userId, userRoleDto.userId)
        UserRoleDto.commonValidator.invoke(userRoleDto)
        val wrapper = KtQueryWrapper(UserRoleEntity::class.java)
            .`in`(UserRoleEntity::userId, listOf(userId))
        val roleSetInDb = userRoleService.list(wrapper)
            .mapNotNull {
                it.roleId
            }
            .toSet()
        val roleSet = userRoleDto.roleList!!.toSet()

        //插入
        val insertEntityList = (roleSet - roleSetInDb)
            .map {
                UserRoleEntity(null, userId, it)
            }
        userRoleService.saveBatch(insertEntityList)

        //删除
        val deleteEntityList = (roleSetInDb - roleSet)
            .map {
                UserRoleEntity(null, userId, it)
            }
        val deleteWrapper = KtQueryWrapper(UserRoleEntity::class.java)
            .eq(UserRoleEntity::userId, userId)
            .`in`(UserRoleEntity::roleId, deleteEntityList)
        userRoleService.remove(deleteWrapper)

        val roleList = userRoleService.list(wrapper)
            .mapNotNull { it.roleId }
        return ResponseUtils
            .successResponse(UserRoleDto(userId, roleList))
    }

}