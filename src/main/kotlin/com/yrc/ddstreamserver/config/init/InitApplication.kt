package com.yrc.ddstreamserver.config.init

import cn.dev33.satoken.secure.SaSecureUtil.md5
import cn.dev33.satoken.secure.SaSecureUtil.md5BySalt
import com.yrc.ddstreamserver.pojo.config.ApplicationConfiguration.*
import com.yrc.ddstreamserver.pojo.keyvaluestore.KeyValueEntity
import com.yrc.ddstreamserver.pojo.permission.PermissionEnumEntity
import com.yrc.ddstreamserver.pojo.role.RoleEntity
import com.yrc.ddstreamserver.pojo.rolepermission.RolePermissionEntity
import com.yrc.ddstreamserver.pojo.user.UserEntity
import com.yrc.ddstreamserver.pojo.userrole.UserRoleEntity
import com.yrc.ddstreamserver.service.keyvaluestore.KeyValueStoreService
import com.yrc.ddstreamserver.service.role.RoleService
import com.yrc.ddstreamserver.service.rolepermission.RolePermissionService
import com.yrc.ddstreamserver.service.user.UserService
import com.yrc.ddstreamserver.service.userrole.UserRoleService
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import javax.annotation.PostConstruct

@Configuration
@DependsOn("flywayInitializer")
class InitApplication(
    private val keyValueStoreService: KeyValueStoreService,
    private val userService: UserService,
    private val roleService: RoleService,
    private val userRoleService: UserRoleService,
    private val rolePermissionService: RolePermissionService,
    @Value("dd-stream.server.password-salt")
    private val salt: String,
) {
    companion object {
        const val ADMIN_USERNAME = "admin"
        const val ADMIN_PASSWORD = "12345678"
        const val ADMIN_ROLE_NAME = "admin"
    }
    @PostConstruct
    fun init() {
        initJwtKey()
        initAdmin()
        initOpenRegister()
        afterInit()
    }

    private fun afterInit() {
        if (!keyValueStoreService.contains(INIT.toString())
            || keyValueStoreService.getById(INIT.toString()).value.toBoolean()
        ) {
            keyValueStoreService.saveOrUpdate(KeyValueEntity(INIT.toString(), true.toString()))
        }
    }

    fun initJwtKey() {
        if (!keyValueStoreService.contains(listOf(JWT_PUBLIC_KEY.toString(),
            JWT_PRIVATE_KEY.toString()))) {
            val keyPair = Keys.keyPairFor(SignatureAlgorithm.ES512)
            val publicKey = Encoders.BASE64.encode(keyPair.public.encoded)
            val privateKey = Encoders.BASE64.encode(keyPair.private.encoded)
            keyValueStoreService.saveOrUpdateBatch(listOf(
                KeyValueEntity(JWT_PUBLIC_KEY.toString(), publicKey),
                KeyValueEntity(JWT_PRIVATE_KEY.toString(), privateKey),
            ))
        }
    }
    fun initAdmin() {
        if (!keyValueStoreService.contains(INIT.toString())
            && keyValueStoreService.getById(INIT.toString()).value.toBoolean()) {
            val userEntity = UserEntity(null, ADMIN_USERNAME, getPassword(ADMIN_PASSWORD), ADMIN_USERNAME, null)
            userService.saveOrUpdate(userEntity)
            roleService.saveOrUpdate(RoleEntity(ADMIN_ROLE_NAME))
            rolePermissionService.saveOrUpdateBatch(
                PermissionEnumEntity.values()
                    .map { RolePermissionEntity(null, ADMIN_ROLE_NAME, it) }
            )
            userRoleService.saveOrUpdate(UserRoleEntity(null, userEntity.id, ADMIN_ROLE_NAME))
        }

    }
    fun initOpenRegister() {
        if (!keyValueStoreService.contains(OPEN_REGISTER.toString())) {
            keyValueStoreService.saveOrUpdate(
                KeyValueEntity(OPEN_REGISTER.toString(), true.toString())
            )
        }
    }
    private fun getPassword(password: String): String {
        return md5BySalt(md5(password), salt)
    }
}