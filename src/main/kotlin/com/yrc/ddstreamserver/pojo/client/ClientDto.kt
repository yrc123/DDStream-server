package com.yrc.ddstreamserver.pojo.client

import com.yrc.common.pojo.common.AbstractDto
import org.valiktor.functions.hasSize
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNull
import org.valiktor.validate

data class ClientDto(
    var id: String? = null,
    var hostname: String? = null,
    var port: Int? = null,
    var nickname: String? = null,
    var note: String? = null,
    var up: Boolean? = null
) : AbstractDto() {
   companion object {
       const val ID_MAX = 64
       const val ID_MIN = 1
       const val NICKNAME_MAX = 16
       const val NICKNAME_MIN = 1
       const val NOTE_MAX = 128
       const val NOTE_MIN = 1
       val updateValidator = { userDto: ClientDto ->
           validate(userDto) {
               validate(ClientDto::id)
                   .isNotNull()
                   .hasSize(ID_MIN, ID_MAX)
               validate(ClientDto::hostname)
                   .isNull()
               validate(ClientDto::port)
                   .isNull()
               validate(ClientDto::nickname)
                   .isNotNull()
                   .hasSize(NICKNAME_MIN, NICKNAME_MAX)
               validate(ClientDto::note)
                   .isNotNull()
                   .hasSize(NOTE_MIN, NOTE_MAX)
               validate(ClientDto::up)
                   .isNull()
           }
       }
       fun updateImmutableValue(clientDto: ClientDto) {
           clientDto.hostname = null
           clientDto.port = null
           clientDto.up = null
       }
   }
}
