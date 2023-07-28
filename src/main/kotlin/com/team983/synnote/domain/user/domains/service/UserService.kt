package com.team983.synnote.domain.user.domains.service

import com.team983.synnote.domain.user.domains.dto.UserCommand
import com.team983.synnote.domain.user.domains.dto.UserInfo

interface UserService {
    fun getUserInfo(userCommand: UserCommand): UserInfo
}
