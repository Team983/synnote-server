package com.team983.synnote.domain.user.domains.service

import com.team983.synnote.domain.user.domains.dto.RegisterUserCommand
import com.team983.synnote.domain.user.domains.dto.UpdateAgreementCommand
import com.team983.synnote.domain.user.domains.dto.UserInfo

interface UserService {
    fun getUserInfo(registerUserCommand: RegisterUserCommand): UserInfo

    fun updateAgreement(updateAgreementCommand: UpdateAgreementCommand): UserInfo

    fun isRegisteredUser(id: String): Boolean
}
