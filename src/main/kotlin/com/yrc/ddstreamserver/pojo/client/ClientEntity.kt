package com.yrc.ddstreamserver.pojo.client

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

@TableName(value = "CLIENT")
data class ClientEntity(
    @TableId(type = IdType.INPUT) var id: String? = null,
    var hostname: String? = null,
    var port: Int? = null,
    var nickname: String? = null,
    var note: String? = null
)
