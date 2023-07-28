package com.team983.synnote.domain.user.domains.dto

import com.team983.synnote.domain.user.domains.entity.Agreement
import java.time.LocalDateTime

data class AgreementInfo(
    val privacyPolicy: Boolean,
    val termsAndCons: Boolean,
    val serviceImprovement: Boolean,
    val privacyPolicyDate: LocalDateTime,
    val termsAndConsDate: LocalDateTime,
    val serviceImprovementDate: LocalDateTime
) {
    constructor(agreement: Agreement) : this(
        privacyPolicy = agreement.privacyPolicy,
        termsAndCons = agreement.termsAndCons,
        serviceImprovement = agreement.serviceImprovement,
        privacyPolicyDate = agreement.privacyPolicyDate!!,
        termsAndConsDate = agreement.termsAndConsDate!!,
        serviceImprovementDate = agreement.serviceImprovementDate!!
    )
}
