package com.yrc.ddstreamserver.service.ffmpeglink.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.yrc.ddstreamserver.dao.ffmpeglink.FFmpegLinkMapper
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkEntity
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkStatusDto
import com.yrc.ddstreamserver.service.feign.ffmpeg.FFmpegService
import com.yrc.ddstreamserver.service.feign.ffmpeg.FFmpegServiceFactory
import com.yrc.ddstreamserver.service.ffmpeglink.FFmpegLinkService
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import java.util.concurrent.Callable

@Service
class FFmpegLinkServiceImpl(
    private val ffmpegLinkMapper: FFmpegLinkMapper,
    private val ffmpegServiceFactory: FFmpegServiceFactory,
    private val threadPoolTaskExecutor: ThreadPoolTaskExecutor
) : FFmpegLinkService, ServiceImpl<FFmpegLinkMapper, FFmpegLinkEntity>(){
    override fun startPush(id: String): Boolean {
        val links = this.listByIds(listOf(id))
        if (links.isNotEmpty()) {
            val ffmpegLinkEntity = links.first()
            threadPoolTaskExecutor.submit {
                ffmpegLinkEntity.ffmpegList!!
                    .forEach {
                        val instance = ffmpegServiceFactory.getServiceInstance(it.clientId!!, FFmpegService::class.java)
                        it.ffmpegConfig!!.id = instance.startPush(it.ffmpegConfig!!.name!!, it.ffmpegConfig!!).data?.id
                        for (i in 1..5) {
                            val ffmpeg = instance.getProcessByName(it.ffmpegConfig!!.name!!).data
                            if (ffmpeg?.alive == true) {
                                Thread.sleep(2000)
                                break
                            } else if (i > 10) {
                                return@forEach
                            }
                            Thread.sleep(500)
                        }
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
                val instance = ffmpegServiceFactory.getServiceInstance(it.clientId!!, FFmpegService::class.java)
                instance.stopPush(it.ffmpegConfig!!.name!!)
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
                try {
                    val instance = ffmpegServiceFactory.getServiceInstance(it.clientId!!,FFmpegService::class.java)
                    FFmpegLinkStatusDto(
                        it.clientId,
                        instance.getProcessByName(it.ffmpegConfig!!.name!!)
                            .data?.alive ?: false
                    )
                } catch (e: Exception) {
                    FFmpegLinkStatusDto(it.clientId, false, e.message)
                }
            }
        }.map {
            threadPoolTaskExecutor.submit(it)
        }.map {
            it.get()
        }
    }
}