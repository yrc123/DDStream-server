package com.yrc.ddstreamserver.dao.client

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yrc.ddstreamserver.pojo.client.ClientEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ClientMapper : BaseMapper<ClientEntity>