package com.team983.synnote.domain.user.domains.service

import com.team983.synnote.domain.user.domains.entity.User

interface UserStore {
    fun registerUser(user: User): User
}
