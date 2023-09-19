package com.team983.synnote.domain.user.interfaces.controller

import com.team983.synnote.common.authority.decodeJwt
import com.team983.synnote.common.dto.BaseResponse
import com.team983.synnote.domain.user.applications.UserFacade
import com.team983.synnote.domain.user.domains.dto.RegisterUserCommand
import com.team983.synnote.domain.user.domains.dto.UpdateAgreementCommand
import com.team983.synnote.domain.user.interfaces.dto.UpdateAgreementRequest
import com.team983.synnote.domain.user.interfaces.dto.UserResponse
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RequestMapping("/api/v1/user")
@RestController
class UserController(
    private val userFacade: UserFacade
) {

    @GetMapping("/login")
    fun redirectForAuthentication(): RedirectView {
        return RedirectView("https://www.synnote.com/contents/auth", true)
    }

    @GetMapping("info")
    @ResponseStatus(OK)
    fun getUserInfo(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String
    ): BaseResponse<UserResponse> {
        log.info("encodedJwt: $encodedJwt")
        val registerUserCommand = RegisterUserCommand(decodeJwt(encodedJwt))
        val userResponse = UserResponse(userFacade.getUserInfo(registerUserCommand))
        return BaseResponse(data = userResponse)
    }

    @PutMapping("agreement")
    @ResponseStatus(OK)
    fun updateAgreement(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String,
        @RequestBody
        @Valid
        updateAgreementRequest: UpdateAgreementRequest
    ): BaseResponse<UserResponse> {
        val updateAgreementCommand = UpdateAgreementCommand(decodeJwt(encodedJwt).sub, updateAgreementRequest)
        val userResponse = UserResponse(userFacade.updateAgreement(updateAgreementCommand))
        return BaseResponse(data = userResponse)
    }

    @PatchMapping("logout")
    @ResponseStatus(OK)
    fun logout(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String,
        response: HttpServletResponse
    ) {
        response.addHeader(
            "Set-Cookie",
            "AWSELBAuthSessionCookie-0=deleted;path=/;expires=Thu, 01 Jan 1970 00:00:00 GMT;"
        )
    }
}
