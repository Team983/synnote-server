package com.team983.synnote.domain.note.domains.service

import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.NoteInfo

interface NoteService {
    fun createNote(createNoteCommand: CreateNoteCommand): NoteInfo
}
