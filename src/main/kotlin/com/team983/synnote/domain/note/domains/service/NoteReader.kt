package com.team983.synnote.domain.note.domains.service

import com.team983.synnote.domain.note.domains.entity.Note
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface NoteReader {
    fun getNoteById(id: Long): Note?

    fun getAllNoteOverview(userId: String, pageable: Pageable): Slice<Note>

    fun existsByIdAndRecordingIsNotNull(noteId: Long): Boolean
}
