package com.yrc.ddstreamserver.controller.common

import com.yrc.ddstreamserver.exception.common.EnumServerException

object ControllerUtils{
    fun checkPathVariable(path: String?, body: String?): Boolean{
        if (path == null
            || body == null
            || path != body) {
            throw EnumServerException.PATH_VARIABLE_NOT_EQUALS.build()
        }
        return true
    }
}
