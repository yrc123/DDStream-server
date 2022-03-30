package com.yrc.ddstreamserver.service.feign.ffmpeg

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.pojo.ffmpeg.FFmpegProcessDto
import org.springframework.web.bind.annotation.*

interface FFmpegService {

    @PostMapping("/api/client/ffmpeg/{name}:start")
    fun startPush(@PathVariable name: String, ffmpegProcessDto: FFmpegProcessDto): ResponseDto<FFmpegProcessDto>

    @GetMapping("/api/client/ffmpeg/{name}:stop")
    fun stopPush(@PathVariable name: String): ResponseDto<String>

    @DeleteMapping("/api/client/ffmpeg/{id}")
    fun deleteProcess(@PathVariable id: String): ResponseDto<String>

    @PostMapping("/api/client/ffmpeg")
    fun listProcesses(@RequestBody page: Page<FFmpegProcessDto>): ResponseDto<Page<FFmpegProcessDto>>

    @GetMapping("/api/client/ffmpeg/{id}")
    fun getFFmpegById(@PathVariable id: String): ResponseDto<FFmpegProcessDto>

}