package com.yrc.ddstreamserver.pojo.rolepermission

import com.yrc.ddstreamserver.pojo.permission.PermissionEnumEntity

data class RolePermissionDto(
    var roleId: String? = null,
    var permissionList: List<PermissionEnumEntity>? = null
)