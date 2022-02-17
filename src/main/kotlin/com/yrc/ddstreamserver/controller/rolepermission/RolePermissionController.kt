package com.yrc.ddstreamserver.controller.rolepermission

import cn.dev33.satoken.annotation.SaCheckPermission
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.controller.common.ControllerUtils
import com.yrc.ddstreamserver.pojo.permission.PermissionName.ROLE_PERMISSION_READ
import com.yrc.ddstreamserver.pojo.permission.PermissionName.ROLE_PERMISSION_WRITE
import com.yrc.ddstreamserver.pojo.rolepermission.RolePermissionDto
import com.yrc.ddstreamserver.pojo.rolepermission.RolePermissionEntity
import com.yrc.ddstreamserver.service.rolepermission.RolePermissionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class RolePermissionController(
    private val rolePermissionService: RolePermissionService
) {
    @SaCheckPermission(ROLE_PERMISSION_READ)
    @GetMapping("/roles/{roleId}/permissions")
    fun listPermissionsByRoleId(@PathVariable roleId: String): ResponseDto<RolePermissionDto> {
        val wrapper = KtQueryWrapper(RolePermissionEntity::class.java)
            .`in`(RolePermissionEntity::roleId, listOf(roleId))
        val permissionList = rolePermissionService.list(wrapper)
            .mapNotNull {
                it.permissionId
            }
        return ResponseUtils
            .successResponse(RolePermissionDto(roleId, permissionList))
    }

    @SaCheckPermission(ROLE_PERMISSION_WRITE)
    @PostMapping("/roles/{roleId}/permissions")
    fun updatePermissions(@PathVariable roleId: String,
                   @RequestBody rolePermissionDto: RolePermissionDto
    ): ResponseDto<RolePermissionDto> {
        ControllerUtils.checkPathVariable(roleId, rolePermissionDto.roleId)
        RolePermissionDto.commonValidator.invoke(rolePermissionDto)
        val wrapper = KtQueryWrapper(RolePermissionEntity::class.java)
            .`in`(RolePermissionEntity::roleId, listOf(roleId))
        val permissionSetInDb = rolePermissionService.list(wrapper)
            .mapNotNull {
                it.permissionId
            }
            .toSet()
        val permissionSet = rolePermissionDto.permissionList!!.toSet()

        //插入
        val insertEntityList = (permissionSet - permissionSetInDb)
            .map {
                RolePermissionEntity(null, roleId, it)
            }
        rolePermissionService.saveBatch(insertEntityList)

        //删除
        val deleteEntityList = (permissionSetInDb - permissionSet)
            .map {
                RolePermissionEntity(null, roleId, it)
            }
        val deleteWrapper = KtQueryWrapper(RolePermissionEntity::class.java)
            .eq(RolePermissionEntity::roleId, roleId)
            .`in`(RolePermissionEntity::permissionId, deleteEntityList)
        rolePermissionService.remove(deleteWrapper)

        val permissionList = rolePermissionService.list(wrapper)
            .mapNotNull { it.permissionId }
        return ResponseUtils
            .successResponse(RolePermissionDto(roleId, permissionList))
    }
}