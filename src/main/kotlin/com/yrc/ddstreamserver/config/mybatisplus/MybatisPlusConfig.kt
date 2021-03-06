package com.yrc.ddstreamserver.config.mybatisplus

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import com.yrc.common.config.mybatisplus.TimeMetaObjectHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class MybatisPlusConfig {
    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor? {
        val interceptor = MybatisPlusInterceptor()
        interceptor.addInnerInterceptor(PaginationInnerInterceptor(DbType.H2))
        return interceptor
    }

    @Bean
    fun timeMetaObjectHandle(): TimeMetaObjectHandler {
        return TimeMetaObjectHandler()
    }
}