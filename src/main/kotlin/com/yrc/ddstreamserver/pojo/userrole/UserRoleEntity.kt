package com.yrc.ddstreamserver.pojo.userrole

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.yrc.common.pojo.common.AbstractEntity

@TableName(value = "USER_ROLE")
data class UserRoleEntity(
    @TableId(type = IdType.ASSIGN_UUID) var id: String? = null,
    var userId: String? = null,
    var roleId: String? = null,
) : AbstractEntity()