package com.yrc.ddstreamserver.pojo.userrole

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

@TableName(value = "USER_ROLE")
data class UserRoleDto (
    @TableId(type = IdType.ASSIGN_UUID) var id: String? = null,
    var userId: String? = null,
    var roleId: String? = null,
)