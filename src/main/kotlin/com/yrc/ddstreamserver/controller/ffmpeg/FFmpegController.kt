package com.yrc.ddstreamserver.controller.ffmpeg

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.pojo.ffmpeg.FFmpegConfigDto
import com.yrc.common.pojo.ffmpeg.FFmpegProcessDto
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.service.feign.ffmpeg.FFmpegServiceFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class FFmpegController(
    private val ffmpegServiceFactory: FFmpegServiceFactory
) {
    @PostMapping("/client/{clientId}/ffmpeg/{id}:start")
    fun startPush(@PathVariable clientId: String, @PathVariable id: String, @RequestBody configDto: FFmpegConfigDto): ResponseDto<FFmpegProcessDto> {
        return ffmpegServiceFactory.getServiceInstance(clientId)
            .startPush(id, configDto)
    }

    @PostMapping("/client/{clientId}/ffmpeg/{id}:start-with-list")
    fun startPushWithString(@PathVariable clientId: String, @PathVariable id: String, @RequestBody configList: List<String>): ResponseDto<FFmpegProcessDto> {
        return ffmpegServiceFactory.getServiceInstance(clientId)
            .startPushWithString(id, configList)
    }

    @GetMapping("/client/{clientId}/ffmpeg/{id}:stop")
    fun stopPush(@PathVariable clientId: String, @PathVariable id: String): ResponseDto<String> {
        return ffmpegServiceFactory.getServiceInstance(clientId)
            .stopPush(id)
    }

    @DeleteMapping("/client/{clientId}/ffmpeg/{id}")
    fun deleteProcess(@PathVariable clientId: String, @PathVariable id: String): ResponseDto<String> {
        return ffmpegServiceFactory.getServiceInstance(clientId)
            .deleteProcess(id)
    }

    @GetMapping("/client/{clientId}/ffmpeg")
    fun listProcesses(@PathVariable clientId: String, page: Page<FFmpegProcessDto>): ResponseDto<Page<FFmpegProcessDto>> {
        return ffmpegServiceFactory.getServiceInstance(clientId)
            .listProcesses(page)
    }

    @GetMapping("/client/{clientId}/ffmpeg/{id}")
    fun getFFmpegById(@PathVariable clientId: String, @PathVariable id: String): ResponseDto<FFmpegProcessDto> {
        return ffmpegServiceFactory.getServiceInstance(clientId)
            .getFFmpegById(id)
    }
}