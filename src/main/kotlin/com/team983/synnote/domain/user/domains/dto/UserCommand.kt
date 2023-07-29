package com.team983.synnote.domain.user.domains.dto

import com.team983.synnote.common.dto.UserAttributeDto
import com.team983.synnote.domain.user.domains.entity.Agreement
import com.team983.synnote.domain.user.domains.entity.User

data class UserCommand(
    val id: String,
    val name: String
) {
    constructor(userAttributeDto: UserAttributeDto) : this(
        id = userAttributeDto.sub,
        name = userAttributeDto.email
    )

    fun toEntity(): User {
        return User(id, name, name, Agreement())
    }
}
