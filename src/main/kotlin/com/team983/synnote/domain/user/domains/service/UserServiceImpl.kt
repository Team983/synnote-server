package com.team983.synnote.domain.user.domains.service

import com.team983.synnote.common.exception.EntityNotFoundException
import com.team983.synnote.common.status.ResultCode.USER_NOT_FOUND
import com.team983.synnote.domain.user.domains.dto.UserAgreementUpdateCommand
import com.team983.synnote.domain.user.domains.dto.UserInfo
import com.team983.synnote.domain.user.domains.dto.UserRegisterCommand
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class UserServiceImpl(
    private val userReader: UserReader,
    private val userStore: UserStore
) : UserService {

    override fun getUserInfo(userRegisterCommand: UserRegisterCommand): UserInfo {
        userReader.getUserInfoById(userRegisterCommand.id)?.let {
            return UserInfo(it)
        }

        val user = userStore.addUser(userRegisterCommand.toEntity())
        return UserInfo(user)
    }

    override fun updateUserAgreement(userAgreementUpdateCommand: UserAgreementUpdateCommand): UserInfo {
        val user = userReader.getUserInfoById(userAgreementUpdateCommand.id)
            ?: throw EntityNotFoundException(USER_NOT_FOUND)
        user.updateAgreement(userAgreementUpdateCommand)
        return UserInfo(user)
    }
}
