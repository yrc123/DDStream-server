package com.yrc.ddstreamserver.service.userrole.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yrc.ddstreamserver.dao.userrole.UserRoleMapper
import com.yrc.ddstreamserver.pojo.userrole.UserRoleEntity
import com.yrc.ddstreamserver.service.userrole.UserRoleService
import org.springframework.stereotype.Service

@Service
class UserRoleServiceImpl : UserRoleService, ServiceImpl<UserRoleMapper, UserRoleEntity>()