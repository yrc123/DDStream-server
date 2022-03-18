package com.yrc.ddstreamserver.pojo.config

data class ConfigDto(
    var jwtPublicKey: String? = null,
    var jwtPrivateKey: String? = null,
    var openRegister: Boolean? = null,

)
