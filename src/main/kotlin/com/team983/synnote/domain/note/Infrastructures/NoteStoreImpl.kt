package com.team983.synnote.domain.note.Infrastructures

import com.team983.synnote.domain.note.domains.entity.Note
import com.team983.synnote.domain.note.domains.service.NoteStore
import org.springframework.stereotype.Component

@Component
class NoteStoreImpl(
    private val noteRepository: NoteRepository
) : NoteStore {

    override fun createNote(note: Note): Note {
        return noteRepository.save(note)
    }
}
