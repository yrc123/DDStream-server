package com.yrc.ddstreamserver.pojo.user

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.yrc.common.pojo.common.AbstractEntity

@TableName(value = "USER")
data class UserEntity(
    @TableId(type = IdType.ASSIGN_UUID) var id: String? = null,
    var username: String? = null,
    var password: String? = null,
    var nickname: String? = null,
    var email: String? = null
) : AbstractEntity()
