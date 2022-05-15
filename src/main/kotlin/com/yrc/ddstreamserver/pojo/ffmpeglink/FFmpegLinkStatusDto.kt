package com.yrc.ddstreamserver.pojo.ffmpeglink

data class FFmpegLinkStatusDto(
    var id: String? = null,
    var name: String? = null,
    var processAlive: Boolean? = null,
    var reason: String? = null,
)
