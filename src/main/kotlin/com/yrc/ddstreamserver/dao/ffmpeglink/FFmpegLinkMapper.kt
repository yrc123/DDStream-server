package com.yrc.ddstreamserver.dao.ffmpeglink

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.yrc.ddstreamserver.pojo.ffmpeglink.FFmpegLinkEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface FFmpegLinkMapper : BaseMapper<FFmpegLinkEntity>{
}