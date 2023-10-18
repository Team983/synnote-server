package com.team983.synnote.domain.note.infrastructures

import com.team983.synnote.domain.note.domains.entity.Memo
import org.springframework.data.jpa.repository.JpaRepository

interface MemoRepository : JpaRepository<Memo, Long>
