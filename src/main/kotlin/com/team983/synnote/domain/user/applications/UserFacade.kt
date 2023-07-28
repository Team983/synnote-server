package com.team983.synnote.domain.user.applications

import com.team983.synnote.domain.user.domains.dto.UserCommand
import com.team983.synnote.domain.user.domains.dto.UserInfo
import com.team983.synnote.domain.user.domains.service.UserService
import org.springframework.stereotype.Service

@Service
class UserFacade(
    private val userService: UserService
) {

    fun getUserInfo(userCommand: UserCommand): UserInfo = userService.getUserInfo(userCommand)
}
