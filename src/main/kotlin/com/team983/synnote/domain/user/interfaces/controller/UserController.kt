package com.team983.synnote.domain.user.interfaces.controller

import com.team983.synnote.common.authority.decodeJwt
import com.team983.synnote.common.dto.BaseResponse
import com.team983.synnote.domain.user.applications.UserFacade
import com.team983.synnote.domain.user.domains.dto.UserAgreementUpdateCommand
import com.team983.synnote.domain.user.domains.dto.UserRegisterCommand
import com.team983.synnote.domain.user.interfaces.dto.UserAgreementDtoRequest
import com.team983.synnote.domain.user.interfaces.dto.UserResponse
import jakarta.validation.Valid
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/user")
@RestController
class UserController(
    private val userFacade: UserFacade
) {

    @GetMapping("info")
    @ResponseStatus(OK)
    fun getUserInfo(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String
    ): BaseResponse<UserResponse> {
        log.info("encodedJwt: $encodedJwt")
        val userCommand = UserRegisterCommand(decodeJwt(encodedJwt))
        val userResponse = UserResponse(userFacade.getUserInfo(userCommand))
        return BaseResponse(data = userResponse)
    }

    @PutMapping("agreement")
    @ResponseStatus(OK)
    fun updateUserAgreement(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String,
        @RequestBody
        @Valid
        userAgreementDtoRequest: UserAgreementDtoRequest
    ): BaseResponse<UserResponse> {
        val userAgreementCommand = UserAgreementUpdateCommand(decodeJwt(encodedJwt).sub, userAgreementDtoRequest)
        val userResponse = UserResponse(userFacade.updateUserAgreement(userAgreementCommand))
        return BaseResponse(data = userResponse)
    }
}
