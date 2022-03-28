package com.yrc.ddstreamserver.pojo.permission

import com.yrc.common.pojo.common.AbstractDto

data class PermissionDto(
    var id: String? = null,
    var description: String? = null
) : AbstractDto() {
    constructor(permissionEnumEntity: PermissionEnumEntity):
            this(permissionEnumEntity.value, permissionEnumEntity.description)
}
