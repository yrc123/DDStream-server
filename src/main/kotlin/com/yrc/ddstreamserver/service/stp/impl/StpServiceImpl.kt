package com.yrc.ddstreamserver.service.stp.impl

import cn.dev33.satoken.stp.StpInterface
import com.yrc.ddstreamserver.pojo.rolepermission.RolePermissionEntity
import com.yrc.ddstreamserver.pojo.userrole.UserRoleEntity
import com.yrc.ddstreamserver.service.role.RoleService
import com.yrc.ddstreamserver.service.rolepermission.RolePermissionService
import com.yrc.ddstreamserver.service.userrole.UserRoleService
import org.springframework.stereotype.Service

@Service
class StpServiceImpl(
    val roleService: RoleService,
    val rolePermissionService: RolePermissionService,
    val userRoleService: UserRoleService,
) : StpInterface {
    override fun getPermissionList(loginId: Any?, loginType: String?): MutableList<String> {
        if (loginId is String) {
            val roleIds = getRoleIdListByUserIdList(listOf(loginId))
            return getPermissionIdListByRoleIdList(roleIds).toMutableList()
        } else {
            throw Exception()
        }
    }

    override fun getRoleList(loginId: Any?, loginType: String?): MutableList<String> {
        if (loginId is String) {
            val roleIds = getRoleIdListByUserIdList(listOf(loginId))
            return roleService.listByIds(roleIds)
                .mapNotNull { it.id }
                .toMutableList()
        } else {
            throw Exception()
        }
    }

    private fun getRoleIdListByUserIdList(userIds: List<String>): List<String> {
        return userRoleService.ktQuery()
            .`in`(UserRoleEntity::userId, userIds.toSet())
            .list()
            .mapNotNull { it.roleId }
    }

    private fun getPermissionIdListByRoleIdList(roleIds: List<String>): List<String> {
        return rolePermissionService.ktQuery()
            .`in`(RolePermissionEntity::roleId, roleIds.toSet())
            .list()
            .mapNotNull { it.permissionId }
            .map { it.value }
            .toSet()
            .toList()
    }
}