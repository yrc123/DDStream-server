package com.yrc.ddstreamserver.pojo.ffmpeglink

import com.yrc.common.pojo.ffmpeg.FFmpegProcessDto
import org.valiktor.functions.hasSize
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class FFmpegLinkItemEntity(
    var clientId: String? = null,
    var ffmpegConfig : FFmpegProcessDto? = null,
) {

    companion object {
        const val CLIENT_ID_MAX = 64
        const val CLIENT_ID_MIN = 1
        val commonValidator = { ffmpegLinkItemEntity : FFmpegLinkItemEntity ->
            validate(ffmpegLinkItemEntity) {
                validate(FFmpegLinkItemEntity::clientId)
                    .isNotNull()
                    .hasSize(CLIENT_ID_MIN, CLIENT_ID_MAX)
                validate(FFmpegLinkItemEntity::ffmpegConfig)
                    .isNotNull()
                FFmpegProcessDto.commonValidator
                    .invoke(ffmpegLinkItemEntity.ffmpegConfig!!)
            }
        }
    }
}
