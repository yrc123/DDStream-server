package com.yrc.ddstreamserver.controller.ffmpeglink

import cn.dev33.satoken.annotation.SaCheckPermission
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkEntity
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkStatusDto
import com.yrc.ddstreamserver.pojo.permission.PermissionName.FFMPEG_LINK_READ
import com.yrc.ddstreamserver.pojo.permission.PermissionName.FFMPEG_LINK_WRITE
import com.yrc.ddstreamserver.service.ffmpeglink.FFmpegLinkService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class FFmpegLinkController(
    private val ffmpegLinkService: FFmpegLinkService,
) {

    @SaCheckPermission(FFMPEG_LINK_WRITE)
    fun startPush(ffmpegLinkEntity: FFmpegLinkEntity): Boolean {
        return ffmpegLinkService.startPush(ffmpegLinkEntity)
    }

    @SaCheckPermission(FFMPEG_LINK_WRITE)
    fun stopPush(id: String): Boolean {
        return ffmpegLinkService.stopPush(id)
    }

    @SaCheckPermission(FFMPEG_LINK_READ)
    fun checkStatus(id: String): List<FFmpegLinkStatusDto> {
        return ffmpegLinkService.checkStatus(id)
    }
}