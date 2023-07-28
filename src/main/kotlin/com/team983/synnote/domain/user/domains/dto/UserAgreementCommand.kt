package com.team983.synnote.domain.user.domains.dto

import com.team983.synnote.domain.user.interfaces.dto.UserAgreementDtoRequest

data class UserAgreementCommand(
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
