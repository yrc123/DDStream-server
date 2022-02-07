package com.yrc.ddstreamserver.dao.role

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yrc.ddstreamserver.pojo.role.RoleDto
import org.apache.ibatis.annotations.Mapper

@Mapper
interface RoleMapper : BaseMapper<RoleDto>