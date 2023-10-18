package com.team983.synnote.domain.note.infrastructures

import com.team983.synnote.domain.note.domains.entity.Script
import org.springframework.data.jpa.repository.JpaRepository

interface ScriptRepository : JpaRepository<Script, Long>
