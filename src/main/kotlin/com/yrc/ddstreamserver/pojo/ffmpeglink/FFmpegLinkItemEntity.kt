package com.yrc.ddstreamserver.pojo.ffmpeglink

import com.yrc.common.pojo.ffmpeg.FFmpegConfigDto

data class FFmpegLinkItemEntity(var id: String? = null,
                                var clientId: String? = null,
                                var config: FFmpegConfigDto? = null,
                                var advancedConfig: List<String>? = null) {
    constructor(id: String, clientId: String, config: FFmpegConfigDto) : this(id, clientId, config, null)
    constructor(id: String, clientId: String, advancedConfig: List<String>) : this(id, clientId, null, advancedConfig)
}
