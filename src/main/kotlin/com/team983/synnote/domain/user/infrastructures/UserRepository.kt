package com.team983.synnote.domain.user.infrastructures

import com.team983.synnote.domain.user.domains.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String>
