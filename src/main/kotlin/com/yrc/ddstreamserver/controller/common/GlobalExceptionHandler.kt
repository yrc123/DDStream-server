package com.yrc.ddstreamserver.controller.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.yrc.common.exception.common.CommonException
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.exception.common.EnumServerException
import feign.FeignException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.annotation.Resource
import javax.servlet.http.HttpServletResponse

@RestControllerAdvice
class GlobalExceptionHandler{
    @Resource
    lateinit var mapper: ObjectMapper

    @ExceptionHandler(CommonException::class)
    fun commonExceptionExceptionHandler(e: CommonException,
                                   res: HttpServletResponse): ResponseDto<ResponseUtils.ExceptionData> {
        res.status = e.getCode()
        return ResponseUtils.exceptionResponse(e)
    }

    @ExceptionHandler(FeignException::class)
    fun badRequestExceptionHandler(e: FeignException,
                                   res: HttpServletResponse): ResponseDto<ResponseUtils.ExceptionData> {
        res.status = e.status()
        val byteArray = e.responseBody().orElseGet(null)?.array()
        val type = mapper.typeFactory
            .constructParametricType(ResponseDto::class.java, ResponseUtils.ExceptionData::class.java)
        return if (byteArray != null) {
            mapper.readValue(byteArray, type)
        } else {
            val exception = EnumServerException.UNKNOWN_FEIGN_BAD_REQUEST_REASON.build()
            ResponseUtils.exceptionResponse(exception)
        }
    }
}