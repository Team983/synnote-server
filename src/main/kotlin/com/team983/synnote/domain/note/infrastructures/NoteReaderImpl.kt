package com.team983.synnote.domain.note.infrastructures

import com.team983.synnote.domain.note.domains.entity.Memo
import com.team983.synnote.domain.note.domains.entity.Note
import com.team983.synnote.domain.note.domains.entity.Script
import com.team983.synnote.domain.note.domains.entity.Summary
import com.team983.synnote.domain.note.domains.service.NoteReader
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class NoteReaderImpl(
    private val noteRepository: NoteRepository,
    private val scriptRespository: ScriptRepository,
    private val memoRepository: MemoRepository,
    private val summaryRepository: SummaryRepository
) : NoteReader {

    override fun getNoteById(id: Long): Note? = noteRepository.findByIdOrNull(id)?.let {
        return it
    }

    override fun getAllNoteOverview(userId: String, pageable: Pageable): Slice<Note> {
        return noteRepository.findByUserIdAndDeletedFlagIsFalse(userId, pageable)
    }

    override fun existsByIdAndRecordingIsNotNull(noteId: Long): Boolean {
        return noteRepository.existsByIdAndRecordingIsNotNull(noteId)
    }

    override fun getScriptById(id: Long): Script? = scriptRespository.findByIdOrNull(id)?.let {
        return it
    }

    override fun getMemoById(id: Long): Memo? = memoRepository.findByIdOrNull(id)?.let {
        return it
    }

    override fun getSummaryByNoteId(noteId: Long): List<Summary> = summaryRepository.findAllByNoteId(noteId)
}
