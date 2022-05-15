package com.yrc.ddstreamserver.service.ffmpeglink

import com.baomidou.mybatisplus.extension.service.IService
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkEntity
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkStatusDto

interface FFmpegLinkService : IService<FFmpegLinkEntity>{
    fun startPush(id: String): Boolean
    fun stopPush(id: String): Boolean
    fun checkStatus(id: String): List<FFmpegLinkStatusDto>
}