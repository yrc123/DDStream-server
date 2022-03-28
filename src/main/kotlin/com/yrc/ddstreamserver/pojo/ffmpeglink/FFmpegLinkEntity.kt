package com.yrc.ddstreamserver.pojo.ffmpeglink

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler
import com.yrc.common.pojo.common.AbstractEntity

@TableName(value = "FFMPEG_LINK")
data class FFmpegLinkEntity(
    @TableId(type = IdType.ASSIGN_UUID) var id: String? = null,
    var name: String? = null,
    @TableField(typeHandler = JacksonTypeHandler::class)
    var ffmpegList: List<FFmpegLinkItemEntity>? = null,
) : AbstractEntity()