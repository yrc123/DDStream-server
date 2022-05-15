package com.yrc.ddstreamserver.pojo.rolepermission

import com.yrc.common.pojo.common.AbstractDto
import com.yrc.ddstreamserver.pojo.permission.PermissionEnumEntity
import com.yrc.ddstreamserver.pojo.role.RoleDto
import org.valiktor.functions.hasSize
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class RolePermissionDto(
    var roleId: String? = null,
    var permissionList: List<PermissionEnumEntity>? = null
) : AbstractDto() {
    companion object {
        val commonValidator = { rolePermissionDto: RolePermissionDto ->
            validate(rolePermissionDto) {
                validate(RolePermissionDto::roleId)
                    .isNotNull()
                    .hasSize(RoleDto.ID_MIN, RoleDto.ID_MAX)
                validate(RolePermissionDto::permissionList).isNotNull()
            }
        }
    }
}