package com.team983.synnote.domain.user.interfaces.controller

import com.team983.synnote.common.authority.decodeJwt
import com.team983.synnote.common.dto.BaseResponse
import com.team983.synnote.domain.user.applications.UserFacade
import com.team983.synnote.domain.user.domains.dto.UserAgreementCommand
import com.team983.synnote.domain.user.domains.dto.UserCommand
import com.team983.synnote.domain.user.interfaces.dto.UserAgreementDtoRequest
import com.team983.synnote.domain.user.interfaces.dto.UserDtoResponse
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

    @GetMapping
    @ResponseStatus(OK)
    fun getUserInfo(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String
    ): BaseResponse<UserDtoResponse> {
        log.info("encodedJwt: $encodedJwt")
        val userCommand = UserCommand(decodeJwt(encodedJwt))
        val userDtoResponse = UserDtoResponse(userFacade.getUserInfo(userCommand))
        return BaseResponse(data = userDtoResponse)
    }

    @PutMapping("agreement")
    @ResponseStatus(OK)
    fun updateUserAgreement(
        //@RequestHeader("x-amzn-oidc-data") encodedJwt: String,
        @RequestBody
        @Valid
        userAgreementDtoRequest: UserAgreementDtoRequest
    ): BaseResponse<UserDtoResponse> {
        //log.info("encodedJwt: $encodedJwt")
        //val userAgreementCommand = UserAgreementCommand(decodeJwt(encodedJwt1).sub, userAgreementDtoRequest)
        val userAgreementCommand = UserAgreementCommand("asdasd", userAgreementDtoRequest) // TODO Delete this line
        val userDtoResponse = UserDtoResponse(userFacade.updateUserAgreement(userAgreementCommand))
        return BaseResponse(data = userDtoResponse)
    }
}
