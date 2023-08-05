package com.team983.synnote.domain.note.infrastructures

import com.team983.synnote.domain.note.domains.entity.Note
import com.team983.synnote.domain.note.domains.service.NoteReader
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class NoteReaderImpl(
    private val noteRepository: NoteRepository
) : NoteReader {

    override fun getNoteById(id: Long): Note? = noteRepository.findByIdOrNull(id)?.let {
        return it
    }

    override fun getAllNoteOverview(userId: String, pageable: Pageable): Slice<Note> {
        return noteRepository.findByUserId(userId, pageable)
    }
}
