package com.yrc.ddstreamserver.controller.role

import cn.dev33.satoken.annotation.SaCheckPermission
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.PageUtils.converterResultPage
import com.yrc.common.utils.PageUtils.converterSearchPage
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.controller.common.ControllerUtils
import com.yrc.ddstreamserver.pojo.permission.PermissionName.ROLE_READ
import com.yrc.ddstreamserver.pojo.permission.PermissionName.ROLE_WRITE
import com.yrc.ddstreamserver.pojo.role.RoleDto
import com.yrc.ddstreamserver.pojo.role.RoleEntity
import com.yrc.ddstreamserver.service.role.RoleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class RoleController(
    private val roleService: RoleService
) {

    @SaCheckPermission(ROLE_READ)
    @GetMapping("/roles")
    fun listRole(page: PageDTO<RoleDto>): ResponseDto<PageDTO<RoleDto>> {
        val searchPage = page.converterSearchPage<RoleDto, RoleEntity>()
        val resultPage = roleService.page(searchPage)
            .converterResultPage(RoleDto::class, ControllerUtils::defaultPageConverterMethod)
        return ResponseUtils.successResponse(resultPage)
    }

    @SaCheckPermission(ROLE_WRITE)
    @PostMapping("/roles/{roleName}")
    fun insertRole(
        @PathVariable roleName: String,
        @RequestBody roleDto: RoleDto
    ): ResponseDto<RoleDto> {
        ControllerUtils.checkPathVariable(roleName, roleDto.id)
        RoleDto.commonValidator.invoke(roleDto)
        val resultDto = ControllerUtils.saveAndReturnDto(
            roleService,
            roleDto,
            RoleDto::class,
            RoleEntity::class
        )
        return ResponseUtils.successResponse(resultDto)
    }

    @SaCheckPermission(ROLE_WRITE)
    @DeleteMapping("/roles/{roleName}")
    fun deleteRole(
        @PathVariable roleName: String,
        @RequestBody roleDto: RoleDto
    ): ResponseDto<String> {
        ControllerUtils.checkPathVariable(roleName, roleDto.id)
        RoleDto.commonValidator.invoke(roleDto)
        roleService.removeById(roleName)
        return ResponseUtils.successStringResponse()
    }

}