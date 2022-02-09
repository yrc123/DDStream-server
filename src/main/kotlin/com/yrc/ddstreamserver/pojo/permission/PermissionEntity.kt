package com.yrc.ddstreamserver.pojo.permission

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

@TableName(value = "PERMISSION")
data class PermissionEntity(
    @TableId(type = IdType.ASSIGN_UUID) var id: String? = null,
    var permissionName: String? = null,
)
