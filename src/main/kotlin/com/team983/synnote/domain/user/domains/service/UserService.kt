package com.team983.synnote.domain.user.domains.service

import com.team983.synnote.domain.user.domains.dto.UserAgreementUpdateCommand
import com.team983.synnote.domain.user.domains.dto.UserInfo
import com.team983.synnote.domain.user.domains.dto.UserRegisterCommand

interface UserService {
    fun getUserInfo(userRegisterCommand: UserRegisterCommand): UserInfo

    fun updateUserAgreement(userAgreementUpdateCommand: UserAgreementUpdateCommand): UserInfo

    fun isRegisteredUser(id: String): Boolean
}
