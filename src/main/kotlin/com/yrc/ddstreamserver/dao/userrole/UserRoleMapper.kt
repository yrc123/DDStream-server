package com.yrc.ddstreamserver.dao.userrole

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yrc.ddstreamserver.pojo.userrole.UserRoleEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserRoleMapper : BaseMapper<UserRoleEntity>