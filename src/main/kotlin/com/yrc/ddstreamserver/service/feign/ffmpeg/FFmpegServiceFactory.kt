package com.yrc.ddstreamserver.service.feign.ffmpeg

import com.yrc.ddstreamserver.service.feign.AbstractFeignClientFactory
import org.springframework.stereotype.Component

@Component
class FFmpegServiceFactory : AbstractFeignClientFactory<FFmpegService>(FFmpegService::class) {
}