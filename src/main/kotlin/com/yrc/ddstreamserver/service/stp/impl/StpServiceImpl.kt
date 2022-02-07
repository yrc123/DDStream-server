package com.yrc.ddstreamserver.service.stp.impl

import cn.dev33.satoken.stp.StpInterface
import com.yrc.ddstreamserver.pojo.rolepermission.RolePermissionDto
import com.yrc.ddstreamserver.pojo.userrole.UserRoleDto
import com.yrc.ddstreamserver.service.permission.PermissionService
import com.yrc.ddstreamserver.service.role.RoleService
import com.yrc.ddstreamserver.service.rolepermission.RolePermissionService
import com.yrc.ddstreamserver.service.userrole.UserRoleService
import org.springframework.stereotype.Service

@Service
class StpServiceImpl(
    val permissionService: PermissionService,
    val roleService: RoleService,
    val rolePermissionService: RolePermissionService,
    val userRoleService: UserRoleService,
) : StpInterface{
    override fun getPermissionList(loginId: Any?, loginType: String?): MutableList<String> {
        if (loginId is String) {
            val roleIds = getRoleIdListByUserIdList(listOf(loginId))
            val permissionIds = getPermissionIdListByRoleIdList(roleIds)
            return permissionService.listByIds(permissionIds)
                .mapNotNull { it.permissionName }
                .toMutableList()
        } else {
            TODO("抛出异常")
        }
    }

    override fun getRoleList(loginId: Any?, loginType: String?): MutableList<String> {
        if (loginId is String) {
            val roleIds = getRoleIdListByUserIdList(listOf(loginId))
            return roleService.listByIds(roleIds)
                .mapNotNull { it.roleName }
                .toMutableList()
        } else {
            TODO("抛出异常")
        }
    }
    private fun getRoleIdListByUserIdList(userIds: List<String>): List<String> {
        return userRoleService.ktQuery()
            .`in`(UserRoleDto::userId, userIds.toSet())
            .list()
            .mapNotNull { it.roleId }
    }
    private fun getPermissionIdListByRoleIdList(roleIds: List<String>): List<String>{
        return rolePermissionService.ktQuery()
            .`in`(RolePermissionDto::roleId, roleIds.toSet())
            .list()
            .mapNotNull { it.permissionId }
    }
}