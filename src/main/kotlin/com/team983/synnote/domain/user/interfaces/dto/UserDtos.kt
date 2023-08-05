package com.team983.synnote.domain.user.interfaces.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.team983.synnote.domain.user.domains.dto.AgreementInfo
import com.team983.synnote.domain.user.domains.dto.UserInfo
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class UserResponse(
    val userId: String,
    val name: String,
    val email: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    val createdDate: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    val updatedDate: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    val removedDate: LocalDateTime,
    val agreement: AgreementInfo
) {
    constructor(userInfo: UserInfo) : this(
        userId = userInfo.id,
        name = userInfo.name,
        email = userInfo.email,
        createdDate = userInfo.createdDate,
        updatedDate = userInfo.updatedDate,
        removedDate = userInfo.removedDate,
        agreement = userInfo.agreement
    )
}

data class UpdateAgreementRequest(
    @field:NotNull
    @JsonProperty("privacyPolicy")
    private val _privacyPolicy: Boolean?,

    @field:NotNull
    @JsonProperty("termsAndCons")
    private val _termsAndCons: Boolean?,

    @field:NotNull
    @JsonProperty("serviceImprovement")
    private val _serviceImprovement: Boolean?
) {
    val privacyPolicy: Boolean
        get() = _privacyPolicy!!

    val termsAndCons: Boolean
        get() = _termsAndCons!!

    val serviceImprovement: Boolean
        get() = _serviceImprovement!!
}
