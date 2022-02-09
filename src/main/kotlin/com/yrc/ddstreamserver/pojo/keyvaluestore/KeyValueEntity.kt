package com.yrc.ddstreamserver.pojo.keyvaluestore

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

@TableName(value = "KEY_VALUE_STORE")
data class KeyValueEntity(
    @TableId(type = IdType.INPUT) var key: String? = null,
    var value: String? = null,
)
