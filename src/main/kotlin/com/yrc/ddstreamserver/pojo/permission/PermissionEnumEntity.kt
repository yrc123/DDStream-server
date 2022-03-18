package com.yrc.ddstreamserver.pojo.permission

import com.baomidou.mybatisplus.annotation.IEnum
import com.baomidou.mybatisplus.annotation.TableName
import com.yrc.ddstreamserver.exception.common.EnumServerException

@TableName(value = "PERMISSION")
enum class PermissionEnumEntity(private val id: String, val description: String? = null) : IEnum<String> {
    USER_READ(PermissionName.USER_READ, "读取用户"),
    USER_WRITE(PermissionName.USER_WRITE, "修改用户"),
    ROLE_READ(PermissionName.ROLE_READ, "读取权限"),
    ROLE_WRITE(PermissionName.ROLE_WRITE, "修改权限"),
    ;

    override fun getValue(): String {
        return id
    }
    companion object {
        val permissionEnumMap = values().associateBy { it.value }
        fun get(id: String): PermissionEnumEntity {
            return permissionEnumMap[id] ?: throw EnumServerException.PERMISSION_NOT_CONTAINS.build()
        }
    }
}
