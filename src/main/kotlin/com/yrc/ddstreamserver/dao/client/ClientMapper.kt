package com.yrc.ddstreamserver.dao.client

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yrc.ddstreamserver.pojo.client.ClientDto
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ClientMapper : BaseMapper<ClientDto>