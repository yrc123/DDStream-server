package com.yrc.ddstreamserver.service.keyvaluestore.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yrc.ddstreamserver.dao.keyvaluestore.KeyValueStoreMapper
import com.yrc.ddstreamserver.pojo.keyvaluestore.KeyValueEntity
import com.yrc.ddstreamserver.service.keyvaluestore.KeyValueStoreService
import org.springframework.stereotype.Service

@Service
class KeyValueStoreServiceImpl : KeyValueStoreService, ServiceImpl<KeyValueStoreMapper, KeyValueEntity>() {

    override fun contains(key: String): Boolean {
        return contains(listOf(key))
    }

    override fun contains(keys: Collection<String>): Boolean {
        return (listByIds(keys) ?: listOf()).size == keys.size
    }
}