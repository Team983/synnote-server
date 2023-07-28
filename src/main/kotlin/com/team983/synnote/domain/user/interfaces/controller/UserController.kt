package com.team983.synnote.domain.user.interfaces.controller

import com.team983.synnote.common.authority.decodeJwt
import com.team983.synnote.common.dto.BaseResponse
import com.team983.synnote.domain.user.applications.UserFacade
import com.team983.synnote.domain.user.domains.dto.UserCommand
import com.team983.synnote.domain.user.interfaces.dto.UserDtoResponse
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
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
}
