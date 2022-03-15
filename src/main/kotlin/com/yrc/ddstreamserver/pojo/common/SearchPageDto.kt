package com.yrc.ddstreamserver.pojo.common

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class SearchPageDto<T>(
    var searchMap: HashMap<String, String>? = null,
    var page: PageDTO<T>? = null,
) {
    companion object{
        val commonValidator = { searchPageDto: SearchPageDto<*> ->
            validate(searchPageDto) {
                validate(SearchPageDto<*>::searchMap)
                    .isNotNull()
                validate(SearchPageDto<*>::page)
                    .isNotNull()
            }
        }
    }
}
