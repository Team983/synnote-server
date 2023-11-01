package com.team983.synnote.domain.note.infrastructures

import com.team983.synnote.domain.note.domains.entity.Summary
import org.springframework.data.jpa.repository.JpaRepository

interface SummaryRepository : JpaRepository<Summary, Long> {
    fun findAllByNoteId(noteId: Long): List<Summary>
}
