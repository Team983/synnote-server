package com.team983.synnote.domain.user.domains.service

import com.team983.synnote.domain.user.domains.dto.RegisterUserCommand
import com.team983.synnote.domain.user.domains.dto.UserAgreementUpdateCommand
import com.team983.synnote.domain.user.domains.dto.UserInfo

interface UserService {
    fun getUserInfo(registerUserCommand: RegisterUserCommand): UserInfo

    fun updateUserAgreement(userAgreementUpdateCommand: UserAgreementUpdateCommand): UserInfo

    fun isRegisteredUser(id: String): Boolean
}
