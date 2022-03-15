package com.yrc.ddstreamserver.service.userrole

import com.baomidou.mybatisplus.extension.service.IService
import com.yrc.ddstreamserver.pojo.userrole.UserRoleEntity

interface UserRoleService : IService<UserRoleEntity> {
    fun listRolesByUserIds(userIds: List<String>): Map<String, List<String>>
}