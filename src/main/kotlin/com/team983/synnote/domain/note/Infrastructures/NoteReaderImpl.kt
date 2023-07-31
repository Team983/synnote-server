package com.team983.synnote.domain.note.Infrastructures

import com.team983.synnote.domain.note.domains.entity.Note
import com.team983.synnote.domain.note.domains.service.NoteReader
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class NoteReaderImpl(
    private val noteRepository: NoteRepository
) : NoteReader {

    override fun getNoteById(id: Long): Note? = noteRepository.findByIdOrNull(id)?.let {
        return it
    }
}
