package com.yrc.ddstreamserver.pojo.client

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

@TableName(value = "CLIENT")
data class ClientDto(
    @TableId(type = IdType.ASSIGN_UUID) var id: String? = null,
    var hostname: String? = null,
    var port: Int? = null,
    var nickname: String? = null,
    var note: String? = null
)
