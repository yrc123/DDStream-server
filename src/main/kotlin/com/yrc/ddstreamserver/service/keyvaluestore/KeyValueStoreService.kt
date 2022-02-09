package com.yrc.ddstreamserver.service.keyvaluestore

import com.baomidou.mybatisplus.extension.service.IService
import com.yrc.ddstreamserver.pojo.keyvaluestore.KeyValueEntity

interface KeyValueStoreService : IService<KeyValueEntity>{
    fun contains(key: String): Boolean
}
