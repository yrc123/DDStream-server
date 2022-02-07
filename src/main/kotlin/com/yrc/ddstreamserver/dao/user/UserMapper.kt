package com.yrc.ddstreamserver.dao.user

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yrc.ddstreamserver.pojo.user.UserDto
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper : BaseMapper<UserDto>