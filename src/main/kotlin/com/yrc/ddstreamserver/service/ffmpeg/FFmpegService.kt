package com.yrc.ddstreamclient.controller.ffmpeg

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.pojo.ffmpeg.FFmpegConfigDto
import com.yrc.common.pojo.ffmpeg.FFmpegProcessDto
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/client")
interface FFmpegService {

    @PostMapping("/ffmpeg/name/{name}:start")
    fun startPush(@PathVariable("name") name: String, @RequestBody configDto: FFmpegConfigDto): ResponseDto<FFmpegProcessDto>

    @PostMapping("/ffmpeg/name/{name}:start-with-list")
    fun startPushWithList(@PathVariable("name") name: String, @RequestBody configList: List<String>): ResponseDto<FFmpegProcessDto>

    @GetMapping("/ffmpeg/{id}:stop")
    fun stopPush(@PathVariable("id") id: String): ResponseDto<String>

    @DeleteMapping("/ffmpeg/{id}")
    fun deleteProcess(@PathVariable("id") id: String): ResponseDto<String>

    @PostMapping("/ffmpeg")
    fun listProcesses(@RequestBody page: Page<FFmpegProcessDto>): ResponseDto<IPage<FFmpegProcessDto>>

    @GetMapping("/ffmpeg/{id}")
    fun getFFmpegById(@PathVariable("id") id: String): ResponseDto<FFmpegProcessDto>

    @GetMapping("/ffmpeg/name/{name}")
    fun getFFmpegByName(@PathVariable("name") name: String): ResponseDto<List<FFmpegProcessDto>>
}