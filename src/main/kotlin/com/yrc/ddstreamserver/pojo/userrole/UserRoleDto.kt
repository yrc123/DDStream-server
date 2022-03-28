package com.yrc.ddstreamserver.pojo.userrole

import com.yrc.common.pojo.common.AbstractDto
import com.yrc.ddstreamserver.pojo.user.UserDto
import org.valiktor.functions.hasSize
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class UserRoleDto(
    var userId: String? = null,
    var roleList: List<String>? = null
) : AbstractDto() {
    companion object {
        val commonValidator = { userRoleDto: UserRoleDto ->
            validate(userRoleDto) {
                validate(UserRoleDto::userId)
                    .isNotNull()
                    .hasSize(UserDto.ID_MIN, UserDto.ID_MAX)
                validate(UserRoleDto::roleList).isNotNull()
            }
        }
    }
}
