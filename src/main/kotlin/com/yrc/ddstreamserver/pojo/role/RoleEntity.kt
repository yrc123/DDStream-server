package com.yrc.ddstreamserver.pojo.role

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

@TableName(value = "ROLE")
data class RoleEntity(
    @TableId(type = IdType.ASSIGN_UUID) var id: String? = null,
    var roleName: String? = null,
)