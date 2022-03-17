package com.yrc.ddstreamserver.controller.role

import cn.dev33.satoken.annotation.SaCheckPermission
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO
import com.yrc.common.pojo.common.ResponseDto
import com.yrc.common.utils.PageUtils.converterResultPage
import com.yrc.common.utils.PageUtils.converterSearchPage
import com.yrc.common.utils.ResponseUtils
import com.yrc.ddstreamserver.controller.common.ControllerUtils
import com.yrc.ddstreamserver.exception.common.EnumServerException
import com.yrc.ddstreamserver.pojo.common.SearchPageDto
import com.yrc.ddstreamserver.pojo.permission.PermissionName.ROLE_READ
import com.yrc.ddstreamserver.pojo.permission.PermissionName.ROLE_WRITE
import com.yrc.ddstreamserver.pojo.role.RoleDto
import com.yrc.ddstreamserver.pojo.role.RoleEntity
import com.yrc.ddstreamserver.service.role.RoleService
import com.yrc.ddstreamserver.service.rolepermission.RolePermissionService
import org.springframework.beans.BeanUtils
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class RoleController(
    private val roleService: RoleService,
    private val rolePermissionService: RolePermissionService,
) {

    @SaCheckPermission(ROLE_READ)
    @PostMapping("/roles:search")
    fun listRoles (@RequestBody searchPageDto: SearchPageDto<RoleDto>): ResponseDto<PageDTO<RoleDto>> {
        SearchPageDto.commonValidator.invoke(searchPageDto)
        val searchPage = searchPageDto.page!!.converterSearchPage<RoleDto, RoleEntity>()
        val queryWrapper = QueryWrapper<RoleEntity>()
        searchPageDto.searchMap!!.forEach {
            queryWrapper.like(it.key.isNotBlank() && it.value.isNotBlank(), it.key, it.value)
        }
        val resultPage = roleService.page(searchPage, queryWrapper)
            .converterResultPage(RoleDto::class, ControllerUtils::defaultPageConverterMethod)
        val roleIds = resultPage.records.mapNotNull { it.id }
        val rolePermissionsMap = rolePermissionService.listPermissionsByRoleIds(roleIds)
        resultPage.records.forEach {
            it.permissionList = rolePermissionsMap[it.id] ?: listOf()
        }
        return ResponseUtils.successResponse(resultPage)
    }
    @GetMapping("/roles")
    fun listRoles(): ResponseDto<List<RoleDto>> {
        val resultDtos = roleService.list()
            .map {
                val roleDto = RoleDto()
                BeanUtils.copyProperties(it, roleDto)
                roleDto
            }
        return ResponseUtils.successResponse(resultDtos)
    }

    @SaCheckPermission(ROLE_WRITE)
    @PostMapping("/roles")
    fun insertRole(@RequestBody roleDto: RoleDto): ResponseDto<RoleDto> {
        RoleDto.commonValidator.invoke(roleDto)
        val resultDto = ControllerUtils.saveAndReturnDto(
            roleService,
            roleDto,
            RoleDto::class,
            RoleEntity::class
        )
        rolePermissionService.savePermissionsByRoleId(resultDto.id!!, roleDto.permissionList!!)
        return ResponseUtils.successResponse(resultDto)
    }

    @SaCheckPermission(ROLE_WRITE)
    @DeleteMapping("/roles/{roleId}")
    fun deleteRole(@PathVariable roleId: String): ResponseDto<String> {
        roleService.removeById(roleId)
        return ResponseUtils.successStringResponse()
    }

    @SaCheckPermission(ROLE_WRITE)
    @PatchMapping("/roles/{roleId}")
    fun updateRole(
        @PathVariable roleId: String,
        @RequestBody roleDto: RoleDto
    ): ResponseDto<RoleDto> {
        ControllerUtils.checkPathVariable(roleId, roleDto.id)
        RoleDto.commonValidator.invoke(roleDto)
        val roleEntities = roleService.listByIds(listOf(roleDto.id))
        if (roleEntities.isNotEmpty()) {
            //更新permission
            val rolePermissionMap = rolePermissionService.listPermissionsByRoleIds(listOf(roleDto.id!!))
            val permissionsInDb = rolePermissionMap[roleDto.id]
                ?.toSet()
                ?: setOf()
            val permissionsInMem = roleDto.permissionList
                ?.toSet()
                ?: setOf()
            val permissionsSave = permissionsInMem - permissionsInDb
            val permissionsRemove = permissionsInDb - permissionsInMem
            rolePermissionService.removePermissionsByRoleId(roleDto.id!!, permissionsRemove)
            rolePermissionService.savePermissionsByRoleId(roleDto.id!!, permissionsSave)
            //回填
            roleDto.permissionList = rolePermissionService
                .listPermissionsByRoleIds(listOf(roleDto.id!!))[roleDto.id] ?: listOf()
            return ResponseUtils.successResponse(roleDto)
        } else {
            throw EnumServerException.NOT_FOUND.build()
        }
    }

}