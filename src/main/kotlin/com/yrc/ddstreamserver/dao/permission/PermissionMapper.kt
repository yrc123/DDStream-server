package com.yrc.ddstreamserver.dao.permission

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yrc.ddstreamserver.pojo.permission.PermissionDto
import org.apache.ibatis.annotations.Mapper

@Mapper
interface PermissionMapper : BaseMapper<PermissionDto>