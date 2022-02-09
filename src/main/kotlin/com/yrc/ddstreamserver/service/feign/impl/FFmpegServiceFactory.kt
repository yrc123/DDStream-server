package com.yrc.ddstreamserver.service.feign.impl

import com.yrc.ddstreamclient.controller.ffmpeg.FFmpegService
import com.yrc.ddstreamserver.service.feign.AbstractFeignClientFactory
import org.springframework.stereotype.Component

@Component
class FFmpegServiceFactory : AbstractFeignClientFactory<FFmpegService>(FFmpegService::class.java){
}