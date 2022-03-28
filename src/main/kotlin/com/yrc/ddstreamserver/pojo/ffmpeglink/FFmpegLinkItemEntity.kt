package com.yrc.ddstreamserver.pojo.ffmpeglink

import com.yrc.common.pojo.ffmpeg.FFmpegConfigDto

data class FFmpegLinkItemEntity(
    var clientId: String? = null,
    var config: FFmpegConfigDto? = null,
    var advancedConfig: List<String>? = null
) {
    constructor(clientId: String, config: FFmpegConfigDto) : this(clientId, config, null)
    constructor(clientId: String, advancedConfig: List<String>) : this(clientId, null, advancedConfig)
}
