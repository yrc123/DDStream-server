package com.yrc.ddstreamserver.pojo.permission

data class PermissionDto(
    var id: String? = null,
    var description: String? = null
) {
    constructor(permissionEnumEntity: PermissionEnumEntity):
            this(permissionEnumEntity.value, permissionEnumEntity.description)
}
