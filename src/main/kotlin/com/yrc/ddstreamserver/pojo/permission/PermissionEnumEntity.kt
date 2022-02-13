package com.yrc.ddstreamserver.pojo.permission

import com.baomidou.mybatisplus.annotation.IEnum
import com.baomidou.mybatisplus.annotation.TableName

@TableName(value = "PERMISSION")
enum class PermissionEnumEntity(name: String) : IEnum<String>{
    USER_READ("USER_READ"),
    USER_WRITE("USER_WRITE"),
    ;

    override fun getValue(): String {
        return name
    }
}
