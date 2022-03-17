package com.yrc.ddstreamserver.service.rolepermission

import com.baomidou.mybatisplus.extension.service.IService
import com.yrc.ddstreamserver.pojo.rolepermission.RolePermissionEntity

interface RolePermissionService : IService<RolePermissionEntity> {

    fun listPermissionsByRoleIds(roleIds: List<String>): Map<String, List<String>>

    fun removePermissionsByRoleId(roleId: String, permissionIds: Collection<String>): Boolean

    fun savePermissionsByRoleId(roleId: String, permissionIds: Collection<String>): Boolean
}