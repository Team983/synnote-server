package com.team983.synnote.domain.user.domains.dto

import com.team983.synnote.domain.user.domains.entity.User
import java.time.LocalDateTime

data class UserInfo(
    val id: String,
    val name: String,
    val email: String,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime,
    val removedDate: LocalDateTime,
    val agreement: AgreementInfo
) {
    constructor(user: User) : this(
        id = user.id,
        name = user.name,
        email = user.email,
        createdDate = user.createdDate!!,
        updatedDate = user.updatedDate!!,
        removedDate = user.removedDate!!,
        agreement = AgreementInfo(user.agreement)
    )
}
