package com.yrc.ddstreamserver.pojo.rolepermission

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

@TableName(value = "ROLE_PERMISSION")
data class RolePermissionDto (
    @TableId(type = IdType.ASSIGN_UUID) var id: String? = null,
    var roleId: String? = null,
    var permissionId: String? = null,
)