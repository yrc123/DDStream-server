package com.yrc.ddstreamserver.controller.common

import com.baomidou.mybatisplus.extension.service.IService
import com.yrc.ddstreamserver.exception.common.EnumServerException
import org.springframework.beans.BeanUtils
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

object ControllerUtils{
    fun<U: Any, V : Any> defaultPageConverterMethod(entity: U, dtoClass: KClass<V>): V{
        val dto = dtoClass.createInstance()
        BeanUtils.copyProperties(entity, dto)
        return dto
    }
    fun checkPathVariable(path: String?, body: String?): Boolean{
        if (path == null
            || body == null
            || path != body) {
            throw EnumServerException.PATH_VARIABLE_NOT_EQUALS.build()
        }
        return true
    }

    fun<U : Any, V : Any> saveAndReturnDto(
        service: IService<U>,
        saveDto: V,
        dtoClass: KClass<V>,
        entityClass: KClass<U>): V{

        val saveEntity = entityClass.createInstance()
        BeanUtils.copyProperties(saveDto, saveEntity)
        service.save(saveEntity)
        val resultDto = dtoClass.createInstance()
        BeanUtils.copyProperties(saveEntity, resultDto)
        return resultDto
    }

    fun<U : Any, V : Any> updateAndReturnDto(
        service: IService<U>,
        saveDto: V,
        entityInDb: U,
        dtoClass: KClass<V>): V{

        BeanUtils.copyProperties(saveDto, entityInDb)
        service.updateById(entityInDb)
        val resultDto = dtoClass.createInstance()
        BeanUtils.copyProperties(saveDto, resultDto)
        return resultDto
    }
}
