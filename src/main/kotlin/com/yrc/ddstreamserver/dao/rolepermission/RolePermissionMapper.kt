package com.yrc.ddstreamserver.dao.rolepermission

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yrc.ddstreamserver.pojo.rolepermission.RolePermissionEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface RolePermissionMapper : BaseMapper<RolePermissionEntity>