package com.yrc.ddstreamserver.pojo.permission

import com.baomidou.mybatisplus.annotation.IEnum
import com.baomidou.mybatisplus.annotation.TableName

@TableName(value = "PERMISSION")
enum class PermissionEnumEntity(private val id: String) : IEnum<String> {
    USER_READ(PermissionName.USER_READ),
    USER_WRITE(PermissionName.USER_WRITE),
    ROLE_READ(PermissionName.ROLE_READ),
    ROLE_WRITE(PermissionName.ROLE_WRITE),
    ROLE_PERMISSION_READ(PermissionName.ROLE_PERMISSION_READ),
    ROLE_PERMISSION_WRITE(PermissionName.ROLE_PERMISSION_WRITE),
    ;

    override fun getValue(): String {
        return id
    }
}
