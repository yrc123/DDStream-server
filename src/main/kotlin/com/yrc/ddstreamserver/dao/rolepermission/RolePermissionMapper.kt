package com.yrc.ddstreamserver.dao.rolepermission

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yrc.ddstreamserver.pojo.rolepermission.RolePermissionDto
import org.apache.ibatis.annotations.Mapper

@Mapper
interface RolePermissionMapper : BaseMapper<RolePermissionDto>