package com.team983.synnote.domain.user.domains.service

import com.team983.synnote.common.exception.EntityNotFoundException
import com.team983.synnote.common.status.ResultCode.USER_NOT_FOUND
import com.team983.synnote.domain.user.domains.dto.UserAgreementCommand
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

    override fun updateUserAgreement(userAgreementCommand: UserAgreementCommand): UserInfo {
        val user = userReader.getUserInfoById(userAgreementCommand.id) ?: throw EntityNotFoundException(USER_NOT_FOUND)
        user.updateAgreement(userAgreementCommand)
        return UserInfo(user)
    }
}
