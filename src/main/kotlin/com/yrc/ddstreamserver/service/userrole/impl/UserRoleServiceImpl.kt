package com.yrc.ddstreamserver.service.userrole.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yrc.ddstreamserver.dao.userrole.UserRoleMapper
import com.yrc.ddstreamserver.pojo.userrole.UserRoleEntity
import com.yrc.ddstreamserver.service.userrole.UserRoleService
import org.springframework.stereotype.Service

@Service
class UserRoleServiceImpl : UserRoleService, ServiceImpl<UserRoleMapper, UserRoleEntity>() {
    override fun listRolesByUserIds(userIds: List<String>): Map<String, List<String>> {
        return ktQuery()
            .`in`(UserRoleEntity::userId, userIds)
            .list()
            .filterNotNull()
            .groupBy({ it.userId!! }, { it.roleId!! })
            .toMap()
    }
}
