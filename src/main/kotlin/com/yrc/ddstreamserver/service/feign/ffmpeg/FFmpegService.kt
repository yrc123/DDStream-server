package com.yrc.ddstreamserver.service.feign.ffmpeg

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.pojo.ffmpeg.FFmpegConfigDto
import com.yrc.common.pojo.ffmpeg.FFmpegProcessDto
import org.springframework.web.bind.annotation.*

interface FFmpegService {

    @PostMapping("/api/client/ffmpeg/{id}:start")
    fun startPush(@PathVariable id: String, @RequestBody configDto: FFmpegConfigDto): ResponseDto<FFmpegProcessDto>

    @PostMapping("/api/client/ffmpeg/{id}:start-with-string")
    fun startPushWithString(@PathVariable id: String, @RequestBody configList: List<String>): ResponseDto<FFmpegProcessDto>

    @GetMapping("/api/client/ffmpeg/{id}:stop")
    fun stopPush(@PathVariable id: String): ResponseDto<String>

    @DeleteMapping("/api/client/ffmpeg/{id}")
    fun deleteProcess(@PathVariable id: String): ResponseDto<String>

    @PostMapping("/api/client/ffmpeg")
    fun listProcesses(@RequestBody page: Page<FFmpegProcessDto>): ResponseDto<Page<FFmpegProcessDto>>

    @GetMapping("/api/client/ffmpeg/{id}")
    fun getFFmpegById(@PathVariable id: String): ResponseDto<FFmpegProcessDto>

}