package com.team983.synnote.domain.user.interfaces.dto

import com.team983.synnote.domain.user.domains.dto.AgreementInfo
import com.team983.synnote.domain.user.domains.dto.UserInfo
import java.time.LocalDateTime

data class UserDtoResponse(
    val id: String,
    val name: String,
    val email: String,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime,
    val removedDate: LocalDateTime,
    val agreement: AgreementInfo
) {
    constructor(userInfo: UserInfo) : this(
        id = userInfo.id,
        name = userInfo.name,
        email = userInfo.email,
        createdDate = userInfo.createdDate,
        updatedDate = userInfo.updatedDate,
        removedDate = userInfo.removedDate,
        agreement = userInfo.agreement
    )
}
