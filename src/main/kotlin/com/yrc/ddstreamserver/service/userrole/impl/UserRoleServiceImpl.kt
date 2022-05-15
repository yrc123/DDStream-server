package com.yrc.ddstreamserver.service.userrole.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
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

    override fun removeRolesByUserId(userId: String, roleIds: Collection<String>): Boolean {
        return this.remove(
            KtQueryWrapper(UserRoleEntity::class.java)
            .`in`(UserRoleEntity::userId, userId)
            .`in`(UserRoleEntity::roleId, roleIds)
        )
    }

    override fun saveRolesByUserId(userId: String, roleIds: Collection<String>): Boolean {
        val saveList = roleIds.map { UserRoleEntity(null, userId, it) }
        return this.saveBatch(saveList)
    }

}
