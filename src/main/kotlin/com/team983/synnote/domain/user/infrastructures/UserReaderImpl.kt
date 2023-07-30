package com.team983.synnote.domain.user.infrastructures

import com.team983.synnote.domain.user.domains.entity.User
import com.team983.synnote.domain.user.domains.service.UserReader
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserReaderImpl(
    private val userRepository: UserRepository
) : UserReader {

    override fun getUserById(id: String): User? = userRepository.findByIdOrNull(id)?.let {
        return it
    }

    override fun isRegisteredUser(id: String): Boolean = userRepository.existsById(id)
}
