package com.yrc.ddstreamserver.pojo.role

import com.yrc.ddstreamserver.pojo.permission.PermissionEnumEntity
import org.valiktor.functions.hasSize
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class RoleDto(
    var id: String? = null,
    var permissionList: List<PermissionEnumEntity>? = null,
) {
    companion object {
        const val ID_MAX = 64
        const val ID_MIN = 1
        val commonValidator = { roleDto: RoleDto ->
            validate(roleDto) {
                validate(RoleDto::id)
                    .isNotNull()
                    .hasSize(ID_MIN, ID_MAX)
            }
        }
    }
}
