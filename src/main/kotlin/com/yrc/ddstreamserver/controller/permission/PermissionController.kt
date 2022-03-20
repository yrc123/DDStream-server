package com.yrc.ddstreamserver.controller.permission

import cn.dev33.satoken.annotation.SaCheckPermission
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.pojo.permission.PermissionDto
import com.yrc.ddstreamserver.pojo.permission.PermissionEnumEntity
import com.yrc.ddstreamserver.pojo.permission.PermissionName.PERMISSION_READ
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class PermissionController {
    val permissionList by lazy {
        PermissionEnumEntity.values().toList().map {
            PermissionDto(it)
        }
    }

    @SaCheckPermission(PERMISSION_READ)
    @GetMapping("/permissions")
    fun listPermissions(): ResponseDto<List<PermissionDto>> {
        return ResponseUtils.successResponse(permissionList)
    }
}