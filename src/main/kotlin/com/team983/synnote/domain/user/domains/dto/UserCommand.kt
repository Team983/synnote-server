package com.team983.synnote.domain.user.domains.dto

import com.team983.synnote.common.dto.UserAttributeDto
import com.team983.synnote.domain.user.domains.entity.Agreement
import com.team983.synnote.domain.user.domains.entity.User

data class UserCommand(
    val id: String,
    val name: String
) {
    constructor(id: UserAttributeDto) : this(
        id = id.sub,
        name = id.email
    )

    fun toEntity(): User {
        return User(id, name, name, Agreement())
    }
}
