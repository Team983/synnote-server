package com.team983.synnote.domain.note.infrastructures

import com.team983.synnote.domain.note.domains.entity.Note
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository : JpaRepository<Note, Long> {

    fun findByUserId(userId: String, pageable: Pageable): Slice<Note>

    fun existsByIdAndRecordingIsNotNull(noteId: Long): Boolean
}
