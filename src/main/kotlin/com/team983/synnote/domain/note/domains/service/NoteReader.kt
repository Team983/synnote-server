package com.team983.synnote.domain.note.domains.service

import com.team983.synnote.domain.note.domains.entity.Note

interface NoteReader {
    fun getNoteById(id: Long): Note?
}
