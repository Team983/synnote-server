package com.team983.synnote.domain.user.domains.dto

import com.team983.synnote.common.dto.UserAttributeDto
import com.team983.synnote.domain.user.domains.entity.Agreement
import com.team983.synnote.domain.user.domains.entity.User
import com.team983.synnote.domain.user.interfaces.dto.UserAgreementDtoRequest

data class RegisterUserCommand(
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

data class UserAgreementUpdateCommand(
    val id: String,
    val privacyPolicy: Boolean,
    val termsAndCons: Boolean,
    val serviceImprovement: Boolean
) {
    constructor(sub: String, userAgreementDtoRequest: UserAgreementDtoRequest) : this(
        id = sub,
        privacyPolicy = userAgreementDtoRequest.privacyPolicy!!,
        termsAndCons = userAgreementDtoRequest.termsAndCons!!,
        serviceImprovement = userAgreementDtoRequest.serviceImprovement!!
    )
}
