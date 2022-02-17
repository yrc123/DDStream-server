package com.yrc.ddstreamserver.service.user.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yrc.ddstreamserver.dao.user.UserMapper
import com.yrc.ddstreamserver.pojo.user.UserEntity
import com.yrc.ddstreamserver.service.user.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService, ServiceImpl<UserMapper, UserEntity>() {
    override fun listByUsernames(usernames: List<String>): List<UserEntity> {
        val wrapper = KtQueryWrapper(UserEntity::class.java)
            .`in`(UserEntity::username, usernames)
        return this.list(wrapper)
    }
}