package com.team983.synnote.domain.note.domains.service

import com.team983.synnote.common.exception.AccessDeniedException
import com.team983.synnote.common.exception.BusinessException
import com.team983.synnote.common.exception.EntityNotFoundException
import com.team983.synnote.common.status.ResultCode.*
import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.DeleteNoteCommand
import com.team983.synnote.domain.note.domains.dto.EndRecordingCommand
import com.team983.synnote.domain.note.domains.dto.GetNoteDetailCommand
import com.team983.synnote.domain.note.domains.dto.GetNoteOverviewListCommand
import com.team983.synnote.domain.note.domains.dto.NoteDetailInfo
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import com.team983.synnote.domain.note.domains.dto.NoteOverviewInfo
import com.team983.synnote.domain.note.domains.dto.NoteRecordingInfo
import com.team983.synnote.domain.note.domains.dto.SaveFullScriptCommand
import com.team983.synnote.domain.note.domains.enums.Status
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import javax.print.attribute.standard.JobState.COMPLETED

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

    override fun hasNoteNoRecording(endRecordingCommand: EndRecordingCommand) {
        if (noteReader.existsByIdAndRecordingIsNotNull(endRecordingCommand.noteId)) {
            throw BusinessException(NOTE_ALREADY_HAS_RECORDING)
        }
    }

    override fun attachRecording(endRecordingCommand: EndRecordingCommand): NoteRecordingInfo {
        val note = noteReader.getNoteById(endRecordingCommand.noteId) ?: throw EntityNotFoundException(NOTE_NOT_FOUND)
        val record = endRecordingCommand.toEntity()
        note.attachRecording(record)
        note.updateStatus(Status.PROCESSING)
        return NoteRecordingInfo(note)
    }

    override fun saveScript(saveFullScriptCommand: SaveFullScriptCommand) {
        val note = noteReader.getNoteById(saveFullScriptCommand.noteId) ?: throw EntityNotFoundException(NOTE_NOT_FOUND)
        saveFullScriptCommand.toScripts().forEach(note::attachScript)
        note.updateStatus(Status.COMPLETED)
    }

    override fun getNoteDetail(noteDetailCommand: GetNoteDetailCommand): NoteDetailInfo {
        val note = noteReader.getNoteById(noteDetailCommand.noteId) ?: throw EntityNotFoundException(NOTE_NOT_FOUND)
        if (!note.isOwnedBy(noteDetailCommand.userId)) {
            throw AccessDeniedException(NOTE_NOT_ACCESSED)
        }
        return NoteDetailInfo(note)
    }

    override fun getNoteOverviewList(getNoteOverviewListCommand: GetNoteOverviewListCommand): List<NoteOverviewInfo> {
        val notesSlice =
            noteReader.getAllNoteOverview(getNoteOverviewListCommand.userId, getNoteOverviewListCommand.pageable)
        return if (notesSlice.isEmpty) emptyList() else notesSlice.content.map { NoteOverviewInfo(it) }
    }


}
