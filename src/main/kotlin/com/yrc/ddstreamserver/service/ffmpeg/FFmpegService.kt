package com.yrc.ddstreamclient.controller.ffmpeg

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.pojo.ffmpeg.FFmpegConfigDto
import com.yrc.common.pojo.ffmpeg.FFmpegProcessDto
import org.springframework.web.bind.annotation.*

//@RequestMapping("/api/client")
interface FFmpegService {

    @PostMapping("/api/client/ffmpeg/name/{name}:start")
    fun startPush(@PathVariable("name") name: String, @RequestBody configDto: FFmpegConfigDto): ResponseDto<FFmpegProcessDto>

    @PostMapping("/api/client/ffmpeg/name/{name}:start-with-list")
    fun startPushWithList(@PathVariable("name") name: String, @RequestBody configList: List<String>): ResponseDto<FFmpegProcessDto>

    @GetMapping("/api/client/ffmpeg/{id}:stop")
    fun stopPush(@PathVariable("id") id: String): ResponseDto<String>

    @DeleteMapping("/api/client/ffmpeg/{id}")
    fun deleteProcess(@PathVariable("id") id: String): ResponseDto<String>

    @PostMapping("/api/client/ffmpeg")
    fun listProcesses(@RequestBody page: Page<FFmpegProcessDto>): ResponseDto<IPage<FFmpegProcessDto>>

    @GetMapping("/api/client/ffmpeg/{id}")
    fun getFFmpegById(@PathVariable("id") id: String): ResponseDto<FFmpegProcessDto>

    @GetMapping("/api/client/ffmpeg/name/{name}")
    fun getFFmpegByName(@PathVariable("name") name: String): ResponseDto<List<FFmpegProcessDto>>
}