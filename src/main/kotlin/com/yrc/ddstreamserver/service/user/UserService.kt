package com.yrc.ddstreamserver.service.user

import com.baomidou.mybatisplus.extension.service.IService
import com.yrc.ddstreamserver.pojo.user.UserEntity

interface UserService : IService<UserEntity> {
    fun listByUsernames(usernames: List<String>): List<UserEntity>
}