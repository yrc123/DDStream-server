package com.yrc.ddstreamserver.pojo.role

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.yrc.common.pojo.common.AbstractEntity

@TableName(value = "ROLE")
class RoleEntity(
    @TableId(type = IdType.INPUT) var id: String? = null,
) : AbstractEntity()