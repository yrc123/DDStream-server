package com.yrc.ddstreamserver.pojo.permission

import com.baomidou.mybatisplus.annotation.IEnum
import com.baomidou.mybatisplus.annotation.TableName
import com.yrc.ddstreamserver.exception.common.EnumServerException

@TableName(value = "PERMISSION")
enum class PermissionEnumEntity(private val id: String, val description: String? = null) : IEnum<String> {
    USER_READ(PermissionName.USER_READ, "读取用户"),
    USER_WRITE(PermissionName.USER_WRITE, "修改用户"),
    ROLE_READ(PermissionName.ROLE_READ, "读取角色"),
    ROLE_WRITE(PermissionName.ROLE_WRITE, "修改角色"),
    CLIENT_READ(PermissionName.CLIENT_READ, "读取推流客户端"),
    CLIENT_WRITE(PermissionName.CLIENT_WRITE, "修改推流客户端"),
    CONFIG_READ(PermissionName.CONFIG_READ, "读取配置"),
    CONFIG_WRITE(PermissionName.CONFIG_WRITE, "修改配置"),
    FFMPEG_READ(PermissionName.FFMPEG_READ, "读取推流客户端FFmpeg"),
    FFMPEG_WRITE(PermissionName.FFMPEG_WRITE, "改推流客户端FFmpeg"),
    FFMPEG_LINK_READ(PermissionName.FFMPEG_LINK_READ, "读取FFmpeg Link"),
    FFMPEG_LINK_WRITE(PermissionName.FFMPEG_LINK_WRITE, "修改FFmpeg Link"),
    PERMISSION_READ(PermissionName.PERMISSION_READ, "读取权限"),
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
