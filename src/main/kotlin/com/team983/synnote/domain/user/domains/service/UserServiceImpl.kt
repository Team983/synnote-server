package com.team983.synnote.domain.user.domains.service

import com.team983.synnote.domain.user.domains.dto.UserCommand
import com.team983.synnote.domain.user.domains.dto.UserInfo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class UserServiceImpl(
    private val userReader: UserReader,
    private val userStore: UserStore
) : UserService {

    override fun getUserInfo(userCommand: UserCommand): UserInfo {
        userReader.getUserInfoById(userCommand.id)?.let {
            return UserInfo(it)
        }

        val user = userStore.addUser(userCommand.toEntity())
        return UserInfo(user)
    }
}
