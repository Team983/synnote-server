package com.team983.synnote.domain.note.domains.service

import com.team983.synnote.common.exception.AccessDeniedException
import com.team983.synnote.common.exception.BusinessException
import com.team983.synnote.common.exception.EntityNotFoundException
import com.team983.synnote.common.status.ResultCode.*
import com.team983.synnote.domain.note.domains.dto.BaseSaveScriptCommand
import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.DeleteNoteCommand
import com.team983.synnote.domain.note.domains.dto.EndRecordingCommand
import com.team983.synnote.domain.note.domains.dto.GetNoteDetailCriterion
import com.team983.synnote.domain.note.domains.dto.GetNoteOverviewListCriterion
import com.team983.synnote.domain.note.domains.dto.NoteDetailInfo
import com.team983.synnote.domain.note.domains.dto.NoteDetailInfo.ScriptInfo
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import com.team983.synnote.domain.note.domains.dto.NoteOverviewInfo
import com.team983.synnote.domain.note.domains.dto.NoteOverviewListInfo
import com.team983.synnote.domain.note.domains.dto.NoteRecordingInfo
import com.team983.synnote.domain.note.domains.dto.UpdateErrorStatusCommand
import com.team983.synnote.domain.note.domains.dto.UpdateMemoCommand
import com.team983.synnote.domain.note.domains.dto.UpdateScriptCommand
import com.team983.synnote.domain.note.domains.dto.UpdateTitleCommand
import com.team983.synnote.domain.note.domains.entity.Recording
import com.team983.synnote.domain.note.domains.enums.DomainType
import com.team983.synnote.domain.note.domains.enums.Status
import com.team983.synnote.domain.note.interfaces.dto.AsrRequestResponse
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

    override fun updateNoteErrorStatus(updateErrorStatusCommand: UpdateErrorStatusCommand) {
        val note =
            noteReader.getNoteById(updateErrorStatusCommand.noteId) ?: throw EntityNotFoundException(NOTE_NOT_FOUND)
        note.updateStatus(updateErrorStatusCommand.status)
    }

    override fun updateTitle(updateTitleCommand: UpdateTitleCommand): NoteInfo =
        noteReader.getNoteById(updateTitleCommand.noteId)?.let { note ->
            if (!note.isOwnedBy(updateTitleCommand.userId)) {
                throw AccessDeniedException(NOTE_NOT_ACCESSED)
            }
            note.updateTitle(updateTitleCommand.title)
            NoteInfo(note)
        } ?: throw EntityNotFoundException(NOTE_NOT_FOUND)

    override fun getDomainType(noteId: Long): DomainType {
        val note = noteReader.getNoteById(noteId) ?: throw EntityNotFoundException(NOTE_NOT_FOUND)
        return note.domainType
    }

    override fun attachRecordingAndMemo(
        asrRequestResponse: AsrRequestResponse,
        endRecordingCommand: EndRecordingCommand
    ): NoteRecordingInfo {
        val note = noteReader.getNoteById(asrRequestResponse.noteId) ?: throw EntityNotFoundException(NOTE_NOT_FOUND)
        if (asrRequestResponse.status == Status.PREPROCESSING_ERROR) {
            val record = Recording(endRecordingCommand.s3ObjectUrl, 0)
            note.attachRecording(record)
            endRecordingCommand.toMemos().forEach(note::attachMemo)
            note.updateStatus(Status.PREPROCESSING_ERROR)
            return NoteRecordingInfo(note)
        }

        val record = asrRequestResponse.toEntity()
        note.attachRecording(record)
        endRecordingCommand.toMemos().forEach(note::attachMemo)
        note.updateStatus(Status.PROCESSING)
        return NoteRecordingInfo(note)
    }

    override fun saveScript(saveScriptCommand: BaseSaveScriptCommand) {
        val note = noteReader.getNoteById(saveScriptCommand.noteId) ?: throw EntityNotFoundException(NOTE_NOT_FOUND)
        saveScriptCommand.toScripts().forEach(note::attachScript)
        note.updateAsrCompleted(Status.COMPLETED, saveScriptCommand.language)
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
    override fun getNoteOverviewList(getNoteOverviewListCriterion: GetNoteOverviewListCriterion): NoteOverviewListInfo {
        val notesSlice =
            noteReader.getAllNoteOverview(getNoteOverviewListCriterion.userId, getNoteOverviewListCriterion.pageable)
        if (notesSlice.isEmpty) {
            return NoteOverviewListInfo(emptyList(), false)
        }
        val noteOverviewInfos = notesSlice.content.map { NoteOverviewInfo(it) }
        return NoteOverviewListInfo(noteOverviewInfos, notesSlice.hasNext())
    }

    override fun updateScript(updateScriptCommand: UpdateScriptCommand): ScriptInfo {
        val note = noteReader.getNoteById(updateScriptCommand.noteId)
            ?: throw EntityNotFoundException(NOTE_NOT_FOUND)
        if (!note.isOwnedBy(updateScriptCommand.userId)) {
            throw AccessDeniedException(NOTE_NOT_ACCESSED)
        }
        val script =
            noteReader.getScriptById(updateScriptCommand.scriptId) ?: throw EntityNotFoundException(SCRIPT_NOTE_FOUND)
        script.updateText(updateScriptCommand.text)
        return ScriptInfo(script)
    }

    override fun updateMemo(updateMemoCommand: UpdateMemoCommand): NoteDetailInfo.MemoInfo {
        val note = noteReader.getNoteById(updateMemoCommand.noteId)
            ?: throw EntityNotFoundException(NOTE_NOT_FOUND)
        if (!note.isOwnedBy(updateMemoCommand.userId)) {
            throw AccessDeniedException(NOTE_NOT_ACCESSED)
        }
        val memo = noteReader.getMemoById(updateMemoCommand.memoId) ?: throw EntityNotFoundException(MEMO_NOTE_FOUND)
        memo.updateText(updateMemoCommand.text)
        return NoteDetailInfo.MemoInfo(memo)
    }
}
