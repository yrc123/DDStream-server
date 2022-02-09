package com.yrc.ddstreamserver.dao.user

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yrc.ddstreamserver.pojo.user.UserEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper : BaseMapper<UserEntity>