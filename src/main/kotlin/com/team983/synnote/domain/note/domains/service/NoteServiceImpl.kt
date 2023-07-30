package com.team983.synnote.domain.note.domains.service

import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class NoteServiceImpl(
    private val noteStore: NoteStore
) : NoteService {

    override fun createNote(createNoteCommand: CreateNoteCommand): NoteInfo {
        val note = noteStore.createNote(createNoteCommand.toEntity())
        return NoteInfo(note)
    }
}
