package com.team983.synnote.domain.user.domains.service

import com.team983.synnote.common.exception.EntityNotFoundException
import com.team983.synnote.common.status.ResultCode.USER_NOT_FOUND
import com.team983.synnote.domain.user.domains.dto.RegisterUserCommand
import com.team983.synnote.domain.user.domains.dto.UserAgreementUpdateCommand
import com.team983.synnote.domain.user.domains.dto.UserInfo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class UserServiceImpl(
    private val userReader: UserReader,
    private val userStore: UserStore
) : UserService {

    override fun getUserInfo(registerUserCommand: RegisterUserCommand): UserInfo {
        userReader.getUserById(registerUserCommand.id)?.let {
            return UserInfo(it)
        }

        val user = userStore.registerUser(registerUserCommand.toEntity())
        return UserInfo(user)
    }

    override fun updateUserAgreement(userAgreementUpdateCommand: UserAgreementUpdateCommand): UserInfo {
        val user = userReader.getUserById(userAgreementUpdateCommand.id)
            ?: throw EntityNotFoundException(USER_NOT_FOUND)
        user.updateAgreement(userAgreementUpdateCommand)
        return UserInfo(user)
    }

    override fun isRegisteredUser(id: String): Boolean = userReader.isRegisteredUser(id)
}
