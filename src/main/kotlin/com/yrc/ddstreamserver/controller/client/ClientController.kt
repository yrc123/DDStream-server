package com.yrc.ddstreamserver.controller.client

import cn.dev33.satoken.annotation.SaCheckPermission
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.controller.common.ControllerUtils
import com.yrc.ddstreamserver.exception.common.EnumServerException
import com.yrc.ddstreamserver.pojo.client.ClientDto
import com.yrc.ddstreamserver.pojo.permission.PermissionName.CLIENT_READ
import com.yrc.ddstreamserver.pojo.permission.PermissionName.CLIENT_WRITE
import com.yrc.ddstreamserver.service.client.ClientService
import org.springframework.beans.BeanUtils
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ClientController(
    private val clientService: ClientService
) {

    @SaCheckPermission(CLIENT_READ)
    @GetMapping("/clients")
    fun listClients(): ResponseDto<List<ClientDto>> {
        val result = clientService.list()
            .filter { it.id != null }
            .mapNotNull {
                val clientDto = ClientDto()
                BeanUtils.copyProperties(it, clientDto)
                clientDto.up = clientService.clientContains(clientDto.id!!)
                clientDto
            }
        return ResponseUtils.successResponse(result)
    }

    @SaCheckPermission(CLIENT_WRITE)
    @DeleteMapping("/clients/{clientId}")
    fun deleteClient(@PathVariable clientId: String): ResponseDto<String> {
        clientService.removeById(clientId)
        return ResponseUtils.successStringResponse()
    }

    @SaCheckPermission(CLIENT_WRITE)
    @PatchMapping("/clients/{clientId}")
    fun updateClient(@PathVariable clientId: String, @RequestBody clientDto: ClientDto): ResponseDto<ClientDto> {
        ClientDto.updateImmutableValue(clientDto)
        ClientDto.updateValidator.invoke(clientDto)
        ControllerUtils.checkPathVariable(clientId, clientDto.id)
        val clientEntities = clientService.listByIds(listOf(clientDto.id))
        if (clientEntities.isNotEmpty()) {
            val resultDto = ControllerUtils.updateAndReturnDto(
                clientService,
                clientDto,
                clientEntities.first(),
                ClientDto::class
            )
            return ResponseUtils.successResponse(resultDto)
        } else {
            throw EnumServerException.NOT_FOUND.build()
        }
    }

}