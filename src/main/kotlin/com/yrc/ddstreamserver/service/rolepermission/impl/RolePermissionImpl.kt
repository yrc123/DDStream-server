package com.yrc.ddstreamserver.service.rolepermission.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yrc.ddstreamserver.dao.rolepermission.RolePermissionMapper
import com.yrc.ddstreamserver.pojo.permission.PermissionEnumEntity
import com.yrc.ddstreamserver.pojo.rolepermission.RolePermissionEntity
import com.yrc.ddstreamserver.service.rolepermission.RolePermissionService
import org.springframework.stereotype.Service

@Service
class RolePermissionImpl : RolePermissionService, ServiceImpl<RolePermissionMapper, RolePermissionEntity>() {

    override fun listPermissionsByRoleIds(roleIds: List<String>): Map<String, List<String>> {
        return ktQuery()
            .`in`(RolePermissionEntity::roleId, roleIds)
            .list()
            .filterNotNull()
            .groupBy({ it.roleId!! }, { it.permissionId!!.value })
            .toMap()
    }

    override fun removePermissionsByRoleId(roleId: String, permissionIds: Collection<String>): Boolean {
        return this.remove(
            KtQueryWrapper(RolePermissionEntity::class.java)
                .`in`(RolePermissionEntity::roleId, roleId)
                .`in`(RolePermissionEntity::permissionId, permissionIds.map { PermissionEnumEntity.get(it) })
        )
    }

    override fun savePermissionsByRoleId(roleId: String, permissionIds: Collection<String>): Boolean {
        val saveList = permissionIds.map { RolePermissionEntity(null, roleId, PermissionEnumEntity.get(it)) }
        return this.saveBatch(saveList)
    }
}