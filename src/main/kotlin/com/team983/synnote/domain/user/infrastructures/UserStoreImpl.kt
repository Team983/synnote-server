package com.team983.synnote.domain.user.infrastructures

import com.team983.synnote.domain.user.domains.entity.User
import com.team983.synnote.domain.user.domains.service.UserStore
import org.springframework.stereotype.Component

@Component
class UserStoreImpl(
    private val userRepository: UserRepository
) : UserStore {

    override fun registerUser(user: User): User {
        return userRepository.save(user)
    }
}
