package com.yrc.ddstreamserver.dao.keyvaluestore

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yrc.ddstreamserver.pojo.keyvaluestore.KeyValueEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface KeyValueStoreMapper : BaseMapper<KeyValueEntity>