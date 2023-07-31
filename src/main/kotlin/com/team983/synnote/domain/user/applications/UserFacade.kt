package com.team983.synnote.domain.user.applications

import com.team983.synnote.domain.user.domains.dto.RegisterUserCommand
import com.team983.synnote.domain.user.domains.dto.UpdateAgreementCommand
import com.team983.synnote.domain.user.domains.dto.UserInfo
import com.team983.synnote.domain.user.domains.service.UserService
import org.springframework.stereotype.Service

@Service
class UserFacade(
    private val userService: UserService
) {

    fun getUserInfo(registerUserCommand: RegisterUserCommand): UserInfo = userService.getUserInfo(registerUserCommand)

    fun updateAgreement(updateAgreementCommand: UpdateAgreementCommand): UserInfo {
        return userService.updateAgreement(updateAgreementCommand)
    }
}
