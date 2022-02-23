package com.yrc.ddstreamserver.service.rolepermission.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yrc.ddstreamserver.dao.rolepermission.RolePermissionMapper
import com.yrc.ddstreamserver.pojo.rolepermission.RolePermissionEntity
import com.yrc.ddstreamserver.service.rolepermission.RolePermissionService
import org.springframework.stereotype.Service

@Service
class RolePermissionImpl : RolePermissionService, ServiceImpl<RolePermissionMapper, RolePermissionEntity>()