package com.yrc.ddstreamserver.controller.permission

import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.pojo.permission.PermissionEnumEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class PermissionController {
    val permissionList by lazy {
        PermissionEnumEntity.values().toList()
    }

    @GetMapping("/permissions")
    fun listPermissions(): ResponseDto<List<PermissionEnumEntity>> {
        return ResponseUtils.successResponse(permissionList)
    }
}