package com.yrc.ddstreamserver.pojo.ffmpeglink

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.yrc.common.pojo.common.AbstractDto
import org.valiktor.functions.hasSize
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class FFmpegLinkDto(
    var id: String? = null,
    var name: String? = null,
    @JsonDeserialize(contentAs = FFmpegLinkItemEntity::class)
    var ffmpegList: List<FFmpegLinkItemEntity>? = null,
) : AbstractDto() {
    companion object {
        const val ID_MAX = 64
        const val ID_MIN = 1
        const val NAME_MAX = 64
        const val NAME_MIN = 1
        val commonValidator = { ffmpegLink: FFmpegLinkDto ->
            validate(ffmpegLink) {
                validate(FFmpegLinkDto::name)
                    .isNotNull()
                    .hasSize(NAME_MIN, NAME_MAX)
                validate(FFmpegLinkDto::ffmpegList)
                    .isNotNull()
                ffmpegLink.ffmpegList!!.forEach{
                    FFmpegLinkItemEntity.commonValidator.invoke(it)
                }
            }
        }
        val updateValidator = { ffmpegLink: FFmpegLinkDto ->
            validate(ffmpegLink) {
                validate(FFmpegLinkDto::id)
                    .isNotNull()
                    .hasSize(ID_MIN, ID_MAX)
                validate(FFmpegLinkDto::name)
                    .isNotNull()
                    .hasSize(NAME_MIN, NAME_MAX)
                validate(FFmpegLinkDto::ffmpegList)
                    .isNotNull()
                ffmpegLink.ffmpegList!!.forEach{
                    FFmpegLinkItemEntity.commonValidator.invoke(it)
                }
            }
        }
    }
}
