package com.yrc.ddstreamserver.pojo.role

data class RoleDto(
    var id: String? = null,
) {
    companion object {
        val ID_MAX = 64
    }
}
