package com.team983.synnote.domain.note.infrastructures

import com.team983.synnote.domain.note.domains.entity.Note
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository : JpaRepository<Note, Long>
