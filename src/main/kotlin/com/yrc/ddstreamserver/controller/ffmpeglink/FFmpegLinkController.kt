package com.yrc.ddstreamserver.controller.ffmpeglink

import cn.dev33.satoken.annotation.SaCheckPermission
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.PageUtils.converterResultPage
import com.yrc.common.utils.PageUtils.converterSearchPage
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.controller.common.ControllerUtils
import com.yrc.ddstreamserver.exception.common.EnumServerException
import com.yrc.ddstreamserver.pojo.common.SearchPageDto
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkDto
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkEntity
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkStatusDto
import com.yrc.ddstreamserver.pojo.permission.PermissionName.FFMPEG_LINK_READ
import com.yrc.ddstreamserver.pojo.permission.PermissionName.FFMPEG_LINK_WRITE
import com.yrc.ddstreamserver.service.ffmpeglink.FFmpegLinkService
import org.apache.commons.beanutils.BeanUtils
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class FFmpegLinkController(
    private val ffmpegLinkService: FFmpegLinkService,
) {

    @SaCheckPermission(FFMPEG_LINK_WRITE)
    @PostMapping("/ffmpeg-link")
    fun insertFFmpegLink(@RequestBody ffmpegLinkDto: FFmpegLinkDto): ResponseDto<String> {
        FFmpegLinkDto.commonValidator.invoke(ffmpegLinkDto)
        val ffmpegLinkEntity = FFmpegLinkEntity()
        BeanUtils.copyProperties(ffmpegLinkEntity, ffmpegLinkDto)
        ffmpegLinkService.save(ffmpegLinkEntity)
        return ResponseUtils.successStringResponse()
    }

    @SaCheckPermission(FFMPEG_LINK_READ)
    @GetMapping("/ffmpeg-link/{id}")
    fun getFFmpegLink(@PathVariable id: String): ResponseDto<FFmpegLinkDto> {
        val ffmpegLinks = ffmpegLinkService.listByIds(listOf(id))
            .filterNotNull()
        if (ffmpegLinks.isEmpty()) {
            throw EnumServerException.NOT_FOUND.build()
        } else {
            val ffmpegLinkDto = FFmpegLinkDto()
            BeanUtils.copyProperties(ffmpegLinkDto, ffmpegLinks.first())
            return ResponseUtils.successResponse(ffmpegLinkDto)
        }
    }

    @SaCheckPermission(FFMPEG_LINK_WRITE)
    @DeleteMapping("/ffmpeg-link/{id}")
    fun deleteFFmpegLink(@PathVariable id: String): ResponseDto<String> {
        ffmpegLinkService.removeById(id)
        return ResponseUtils.successStringResponse()
    }

    @SaCheckPermission(FFMPEG_LINK_WRITE)
    @GetMapping("/ffmpeg-link/{id}:start")
    fun startPush(@PathVariable id: String): ResponseDto<String> {
        ffmpegLinkService.startPush(id)
        return ResponseUtils.successStringResponse()
    }

    @SaCheckPermission(FFMPEG_LINK_WRITE)
    @GetMapping("/ffmpeg-link/{id}:stop")
    fun stopPush(@PathVariable id: String): ResponseDto<String> {
        ffmpegLinkService.stopPush(id)
        return ResponseUtils.successStringResponse()
    }

    @SaCheckPermission(FFMPEG_LINK_READ)
    @GetMapping("/ffmpeg-link/{id}:status")
    fun checkStatus(@PathVariable id: String): ResponseDto<List<FFmpegLinkStatusDto>> {
        return ResponseUtils.successResponse(ffmpegLinkService.checkStatus(id))
    }

    @SaCheckPermission(FFMPEG_LINK_READ)
    @PostMapping("/ffmpeg-link:search")
    fun listFFmpegLink(@RequestBody searchPageDto: SearchPageDto<FFmpegLinkDto>): ResponseDto<PageDTO<FFmpegLinkDto>> {
        SearchPageDto.commonValidator.invoke(searchPageDto)
        val searchPage = searchPageDto.page!!.converterSearchPage<FFmpegLinkDto, FFmpegLinkEntity>()
        val queryWrapper = QueryWrapper<FFmpegLinkEntity>()
        searchPageDto.searchMap!!.forEach {
            queryWrapper.like(it.key.isNotBlank() && it.value.isNotBlank(), it.key, it.value)
        }
        val resultPage = ffmpegLinkService.page(searchPage, queryWrapper)
            .converterResultPage(FFmpegLinkDto::class, ControllerUtils::defaultPageConverterMethod)
        return ResponseUtils.successResponse(resultPage)
    }
    @SaCheckPermission(FFMPEG_LINK_WRITE)
    @PatchMapping("/ffmpeg-link/{id}")
    fun updateFFmpegLink(@PathVariable id: String, @RequestBody ffmpegLinkDto: FFmpegLinkDto): ResponseDto<FFmpegLinkDto> {
        FFmpegLinkDto.updateValidator.invoke(ffmpegLinkDto)
        ControllerUtils.checkPathVariable(id, ffmpegLinkDto.id)
        val userEntities = ffmpegLinkService.listByIds(listOf(ffmpegLinkDto.id))
        if (userEntities.isNotEmpty()) {
            val resultDto = ControllerUtils.updateAndReturnDto(
                ffmpegLinkService,
                ffmpegLinkDto,
                userEntities.first(),
                FFmpegLinkDto::class
            )
            return ResponseUtils.successResponse(resultDto)
        } else {
            throw EnumServerException.NOT_FOUND.build()
        }
    }
}