package com.yrc.ddstreamserver.service.ffmpeglink.impl

import com.yrc.ddstreamserver.dao.ffmpeglink.FFmpegLinkMapper
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkEntity
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkStatusDto
import com.yrc.ddstreamserver.service.feign.ffmpeg.FFmpegServiceFactory
import com.yrc.ddstreamserver.service.ffmpeglink.FFmpegLinkService
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import java.util.concurrent.Callable

@Service
class FFmpegLinkServiceImpl(
    private val  ffmpegLinkMapper: FFmpegLinkMapper,
    private val ffmpegServiceFactory: FFmpegServiceFactory,
    private val threadPoolTaskExecutor: ThreadPoolTaskExecutor
) : FFmpegLinkService {
    @Suppress("UNREACHABLE_CODE")
    override fun startPush(ffmpegLinkEntity: FFmpegLinkEntity): Boolean {
        ffmpegLinkMapper.insert(ffmpegLinkEntity)
        threadPoolTaskExecutor.submit {
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
                        } else if (i == 5) {
                            return@forEach
                        }
                        Thread.sleep(500)
                    }
                }
        }
        return true
    }

    override fun stopPush(id: String): Boolean {
        val ffmpegLinkEntity = ffmpegLinkMapper.selectById(id)
            ?: return true
        ffmpegLinkEntity.ffmpegList!!.map {
            Runnable {
                val instance = ffmpegServiceFactory.getServiceInstance(it.clientId!!)
                instance.stopPush(it.id!!)
            }
        }.map {
            threadPoolTaskExecutor.submit(it)
        }
        return true
    }

    override fun checkStatus(id: String): List<FFmpegLinkStatusDto> {
        val ffmpegLinkEntity = ffmpegLinkMapper.selectById(id)
            ?: return listOf()
        return ffmpegLinkEntity.ffmpegList!!.map {
            Callable {
                val instance = ffmpegServiceFactory.getServiceInstance(it.clientId!!)
                FFmpegLinkStatusDto(
                    it.id,
                    try {
                        instance.getFFmpegById(it.id!!)
                            .data
                            ?.alive ?: false
                    } catch (e: Exception) {
                        false
                    }
                )
            }
        }.map {
            threadPoolTaskExecutor.submit(it)
        }.map {
            it.get()
        }
    }
}