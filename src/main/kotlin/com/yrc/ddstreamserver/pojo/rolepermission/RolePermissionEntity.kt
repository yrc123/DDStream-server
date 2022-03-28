package com.yrc.ddstreamserver.pojo.rolepermission

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.yrc.common.pojo.common.AbstractEntity
import com.yrc.ddstreamserver.pojo.permission.PermissionEnumEntity

@TableName(value = "ROLE_PERMISSION")
data class RolePermissionEntity(
    @TableId(type = IdType.ASSIGN_UUID) var id: String? = null,
    var roleId: String? = null,
    var permissionId: PermissionEnumEntity? = null,
) : AbstractEntity()