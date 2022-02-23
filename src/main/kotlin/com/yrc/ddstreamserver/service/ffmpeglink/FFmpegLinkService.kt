package com.yrc.ddstreamserver.service.ffmpeglink

import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkEntity
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkStatusDto

interface FFmpegLinkService {

    fun startPush(ffmpegLinkEntity: FFmpegLinkEntity): Boolean
    fun stopPush(id: String): Boolean
    fun checkStatus(id: String): List<FFmpegLinkStatusDto>
}