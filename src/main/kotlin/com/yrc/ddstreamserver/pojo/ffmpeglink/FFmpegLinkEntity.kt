package com.yrc.ddstreamserver.pojo.ffmpeglink

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.yrc.common.pojo.common.AbstractEntity
import com.yrc.common.pojo.common.AbstractArrayTypeHandler

@TableName(value = "FFMPEG_LINK", autoResultMap = true)
data class FFmpegLinkEntity(
    @TableId(type = IdType.ASSIGN_UUID)
    var id: String? = null,
    var name: String? = null,
    @TableField(typeHandler = FFmpegLinkItemEntityArray::class)
    var ffmpegList: List<FFmpegLinkItemEntity>? = null,
) : AbstractEntity() {
    companion object {
        class FFmpegLinkItemEntityArray : AbstractArrayTypeHandler<List<FFmpegLinkItemEntity>>(
            object : TypeReference<List<FFmpegLinkItemEntity>>(){},
            ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        )
    }
}