package com.team983.synnote.domain.note.domains.service

import com.team983.synnote.common.exception.AccessDeniedException
import com.team983.synnote.common.exception.EntityNotFoundException
import com.team983.synnote.common.status.ResultCode.*
import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.DeleteNoteCommand
import com.team983.synnote.domain.note.domains.dto.EndRecordingCommand
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import com.team983.synnote.domain.note.domains.dto.NoteRecordingInfo
import com.team983.synnote.domain.note.domains.enums.Status
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class NoteServiceImpl(
    private val noteStore: NoteStore,
    private val noteReader: NoteReader
) : NoteService {

    override fun createNote(createNoteCommand: CreateNoteCommand): NoteInfo {
        val note = noteStore.createNote(createNoteCommand.toEntity())
        return NoteInfo(note)
    }

    override fun deleteNote(deleteNoteCommand: DeleteNoteCommand): NoteInfo =
        noteReader.getNoteById(deleteNoteCommand.noteId)?.let { note ->
            if (!note.isOwnedBy(deleteNoteCommand.userId)) {
                throw AccessDeniedException(NOTE_NOT_ACCESSED)
            }
            note.delete()
            NoteInfo(note)
        } ?: throw EntityNotFoundException(NOTE_NOT_FOUND)

    override fun attachRecording(endRecordingCommand: EndRecordingCommand): NoteRecordingInfo {
        val note = noteReader.getNoteById(endRecordingCommand.noteId) ?: throw EntityNotFoundException(NOTE_NOT_FOUND)
        val record = endRecordingCommand.toEntity()
        note.attachRecording(record)
        note.updateStatus(Status.PROCESSING)
        return NoteRecordingInfo(note)
    }
}
