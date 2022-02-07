package com.yrc.ddstreamserver.service.permission.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yrc.ddstreamserver.dao.permission.PermissionMapper
import com.yrc.ddstreamserver.pojo.permission.PermissionDto
import com.yrc.ddstreamserver.service.permission.PermissionService
import org.springframework.stereotype.Service

@Service
class PermissionServiceImpl : PermissionService, ServiceImpl<PermissionMapper, PermissionDto>()