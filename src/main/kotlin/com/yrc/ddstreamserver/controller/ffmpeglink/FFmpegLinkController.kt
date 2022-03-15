package com.yrc.ddstreamserver.controller.ffmpeglink

import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkEntity
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkStatusDto
import com.yrc.ddstreamserver.service.ffmpeglink.FFmpegLinkService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class FFmpegLinkController(
    private val ffmpegLinkService: FFmpegLinkService,
) {

    fun startPush(ffmpegLinkEntity: FFmpegLinkEntity): Boolean {
        return ffmpegLinkService.startPush(ffmpegLinkEntity)
    }

    fun stopPush(id: String): Boolean {
        return ffmpegLinkService.stopPush(id)
    }

    fun checkStatus(id: String): List<FFmpegLinkStatusDto> {
        return ffmpegLinkService.checkStatus(id)
    }
}