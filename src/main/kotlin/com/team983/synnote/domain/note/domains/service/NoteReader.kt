package com.team983.synnote.domain.note.domains.service

import com.team983.synnote.domain.note.domains.entity.Memo
import com.team983.synnote.domain.note.domains.entity.Note
import com.team983.synnote.domain.note.domains.entity.Script
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface NoteReader {
    fun getNoteById(id: Long): Note?

    fun getAllNoteOverview(userId: String, pageable: Pageable): Slice<Note>

    fun existsByIdAndRecordingIsNotNull(noteId: Long): Boolean

    fun getScriptById(id: Long): Script?

    fun getMemoById(id: Long): Memo?
}
