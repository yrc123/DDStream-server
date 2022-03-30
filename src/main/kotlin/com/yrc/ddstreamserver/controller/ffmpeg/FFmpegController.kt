package com.yrc.ddstreamserver.controller.ffmpeg

import cn.dev33.satoken.annotation.SaCheckPermission
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.pojo.ffmpeg.FFmpegProcessDto
import com.yrc.ddstreamserver.pojo.permission.PermissionName.FFMPEG_READ
import com.yrc.ddstreamserver.pojo.permission.PermissionName.FFMPEG_WRITE
import com.yrc.ddstreamserver.service.feign.ffmpeg.FFmpegServiceFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
//TODO: 添加校验
class FFmpegController(
    private val ffmpegServiceFactory: FFmpegServiceFactory
) {
    @SaCheckPermission(FFMPEG_WRITE)
    @PostMapping("/client/{clientId}/ffmpeg/{id}:start")
    fun startPush(@PathVariable clientId: String, @PathVariable id: String, @RequestBody ffmpegProcessDto: FFmpegProcessDto): ResponseDto<FFmpegProcessDto> {
        return ffmpegServiceFactory.getServiceInstance(clientId)
            .startPush(id, ffmpegProcessDto)
    }

    @SaCheckPermission(FFMPEG_WRITE)
    @GetMapping("/client/{clientId}/ffmpeg/{id}:stop")
    fun stopPush(@PathVariable clientId: String, @PathVariable id: String): ResponseDto<String> {
        return ffmpegServiceFactory.getServiceInstance(clientId)
            .stopPush(id)
    }

    @SaCheckPermission(FFMPEG_WRITE)
    @DeleteMapping("/client/{clientId}/ffmpeg/{id}")
    fun deleteProcess(@PathVariable clientId: String, @PathVariable id: String): ResponseDto<String> {
        return ffmpegServiceFactory.getServiceInstance(clientId)
            .deleteProcess(id)
    }

    @SaCheckPermission(FFMPEG_READ)
    @GetMapping("/client/{clientId}/ffmpeg")
    fun listProcesses(@PathVariable clientId: String, page: Page<FFmpegProcessDto>): ResponseDto<Page<FFmpegProcessDto>> {
        return ffmpegServiceFactory.getServiceInstance(clientId)
            .listProcesses(page)
    }

    @SaCheckPermission(FFMPEG_READ)
    @GetMapping("/client/{clientId}/ffmpeg/{id}")
    fun getFFmpegById(@PathVariable clientId: String, @PathVariable id: String): ResponseDto<FFmpegProcessDto> {
        return ffmpegServiceFactory.getServiceInstance(clientId)
            .getFFmpegById(id)
    }
}