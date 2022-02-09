package com.yrc.ddstreamserver.service.role.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yrc.ddstreamserver.dao.role.RoleMapper
import com.yrc.ddstreamserver.pojo.role.RoleEntity
import com.yrc.ddstreamserver.service.role.RoleService
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl : RoleService, ServiceImpl<RoleMapper, RoleEntity>()