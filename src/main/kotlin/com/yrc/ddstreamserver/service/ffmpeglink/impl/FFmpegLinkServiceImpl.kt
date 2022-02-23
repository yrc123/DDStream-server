package com.yrc.ddstreamserver.service.ffmpeglink.impl

import com.yrc.ddstreamserver.dao.ffmpeglink.FFmpegLinkMapper
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkEntity
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkStatusDto
import com.yrc.ddstreamserver.service.feign.ffmpeg.FFmpegServiceFactory
import com.yrc.ddstreamserver.service.ffmpeglink.FFmpegLinkService
import kotlin.concurrent.thread

class FFmpegLinkServiceImpl(
    private val  ffmpegLinkMapper: FFmpegLinkMapper,
    private val ffmpegServiceFactory: FFmpegServiceFactory
) : FFmpegLinkService {
    @Suppress("UNREACHABLE_CODE")
    override fun startPush(ffmpegLinkEntity: FFmpegLinkEntity): Boolean {
        ffmpegLinkMapper.insert(ffmpegLinkEntity)
        thread {
            ffmpegLinkEntity.ffmpegList!!
                .forEach {
                    val instance = ffmpegServiceFactory.getServiceInstance(it.clientId!!)
                    if (it.config == null) {
                        instance.startPushWithString(it.id!!, it.advancedConfig!!)
                    } else {
                        instance.startPush(it.id!!, it.config!!)
                    }
                    for (i in 1..5) {
                        val ffmpeg = instance.getFFmpegById(it.id!!).data
                        if (ffmpeg?.alive == true) {
                            break
                        } else if (i == 5){
                            return@forEach
                        }
                        Thread.sleep(1000)
                    }
                }
        }
        return true
    }

    override fun stopPush(id: String): Boolean {
        TODO("Not yet implemented")
        return true
    }

    override fun checkStatus(id: String): List<FFmpegLinkStatusDto> {
        val ffmpegLinkEntity = ffmpegLinkMapper.selectById(id)
            ?: return listOf()
        return ffmpegLinkEntity.ffmpegList!!.map {
            FFmpegLinkStatusDto(
                it.id,
                ffmpegServiceFactory.getServiceInstance(it.clientId!!)
                    .getFFmpegById(it.id!!)
                    .data
                    ?.alive ?: false
            )
        }
    }
}