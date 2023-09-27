package com.team983.synnote.domain.note.domains.service

import com.team983.synnote.common.exception.AccessDeniedException
import com.team983.synnote.common.exception.BusinessException
import com.team983.synnote.common.exception.EntityNotFoundException
import com.team983.synnote.common.status.ResultCode.*
import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.DeleteNoteCommand
import com.team983.synnote.domain.note.domains.dto.EndRecordingCommand
import com.team983.synnote.domain.note.domains.dto.GetNoteDetailCriterion
import com.team983.synnote.domain.note.domains.dto.GetNoteOverviewListCriterion
import com.team983.synnote.domain.note.domains.dto.NoteDetailInfo
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import com.team983.synnote.domain.note.domains.dto.NoteOverviewInfo
import com.team983.synnote.domain.note.domains.dto.NoteRecordingInfo
import com.team983.synnote.domain.note.domains.dto.SaveFullScriptCommand
import com.team983.synnote.domain.note.domains.dto.UpdateTitleCommand
import com.team983.synnote.domain.note.domains.enums.Status
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    override fun updateNoteErrorStatus(noteId: Long) {
        val note = noteReader.getNoteById(noteId) ?: throw EntityNotFoundException(NOTE_NOT_FOUND)
        note.updateStatus(Status.ERROR)
    }

    override fun updateTitle(updateTitleCommand: UpdateTitleCommand): NoteInfo =
        noteReader.getNoteById(updateTitleCommand.noteId)?.let { note ->
            if (!note.isOwnedBy(updateTitleCommand.userId)) {
                throw AccessDeniedException(NOTE_NOT_ACCESSED)
            }
            note.updateTitle(updateTitleCommand.title)
            NoteInfo(note)
        } ?: throw EntityNotFoundException(NOTE_NOT_FOUND)

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

    @Transactional(readOnly = true)
    override fun getNoteDetail(getNoteDetailCriterion: GetNoteDetailCriterion): NoteDetailInfo {
        val note = noteReader.getNoteById(getNoteDetailCriterion.noteId)
            ?: throw EntityNotFoundException(NOTE_NOT_FOUND)
        if (!note.isOwnedBy(getNoteDetailCriterion.userId)) {
            throw AccessDeniedException(NOTE_NOT_ACCESSED)
        }
        return NoteDetailInfo(note)
    }

    @Transactional(readOnly = true)
    override fun getNoteOverviewList(getNoteOverviewListCriterion: GetNoteOverviewListCriterion):
        List<NoteOverviewInfo> {
        val notesSlice =
            noteReader.getAllNoteOverview(getNoteOverviewListCriterion.userId, getNoteOverviewListCriterion.pageable)
        return if (notesSlice.isEmpty) emptyList() else notesSlice.content.map { NoteOverviewInfo(it) }
    }
}
