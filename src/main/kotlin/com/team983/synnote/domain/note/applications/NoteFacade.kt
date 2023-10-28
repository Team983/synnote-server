package com.team983.synnote.domain.note.applications

import com.team983.synnote.common.exception.EntityNotFoundException
import com.team983.synnote.common.status.ResultCode.*
import com.team983.synnote.common.utils.RestTemplateRequester
import com.team983.synnote.domain.note.domains.dto.BaseSaveScriptCommand
import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.DeleteNoteCommand
import com.team983.synnote.domain.note.domains.dto.EndRecordingCommand
import com.team983.synnote.domain.note.domains.dto.GetNoteDetailCriterion
import com.team983.synnote.domain.note.domains.dto.GetNoteOverviewListCriterion
import com.team983.synnote.domain.note.domains.dto.NoteDetailInfo
import com.team983.synnote.domain.note.domains.dto.NoteDetailInfo.*
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import com.team983.synnote.domain.note.domains.dto.NoteOverviewListInfo
import com.team983.synnote.domain.note.domains.dto.NoteRecordingInfo
import com.team983.synnote.domain.note.domains.dto.UpdateErrorStatusCommand
import com.team983.synnote.domain.note.domains.dto.UpdateMemoCommand
import com.team983.synnote.domain.note.domains.dto.UpdateScriptCommand
import com.team983.synnote.domain.note.domains.dto.UpdateTitleCommand
import com.team983.synnote.domain.note.domains.enums.DomainType
import com.team983.synnote.domain.note.domains.service.NoteService
import com.team983.synnote.domain.note.interfaces.dto.AsrRequestResponse
import com.team983.synnote.domain.user.domains.service.UserService
import org.springframework.stereotype.Service

@Service
class NoteFacade(
    private val noteService: NoteService,
    private val userService: UserService,
    private val restTemplateRequester: RestTemplateRequester
) {
    fun createNote(createNoteCommand: CreateNoteCommand): NoteInfo {
        userService.isRegisteredUser(createNoteCommand.userId).takeIf { it }
            ?: throw EntityNotFoundException(USER_NOT_FOUND)
        return noteService.createNote(createNoteCommand)
    }

    fun deleteNote(deleteNoteCommand: DeleteNoteCommand): NoteInfo = noteService.deleteNote(deleteNoteCommand)

    fun endRecording(endRecordingCommand: EndRecordingCommand): NoteRecordingInfo {
        noteService.hasNoteNoRecording(endRecordingCommand)
        val domainType = noteService.getDomainType(endRecordingCommand.noteId)
        val asrRequestResponse = sendRequestAsrService(endRecordingCommand, domainType)
        return noteService.attachRecordingAndMemo(asrRequestResponse, endRecordingCommand)
    }

    private fun sendRequestAsrService(
        endRecordingCommand: EndRecordingCommand,
        domainType: DomainType
    ): AsrRequestResponse {
        return restTemplateRequester.sendRequestToWhisperX(
            endRecordingCommand.noteId,
            endRecordingCommand.encodedFileName,
            domainType.name,
            AsrRequestResponse::class.java
        )
//        return restTemplateRequester.sendRequestToWhisper(
//            "http://172.20.245.195:8081/asr" +
//                "/${endRecordingCommand.noteId}?filename=${endRecordingCommand.encodedFileName}",
//            AsrRequestResponse::class.java
//        )
    }

    fun saveScript(saveScriptCommand: BaseSaveScriptCommand) {
        val noteDetailInfo = noteService.saveScript(saveScriptCommand)
        //sendNoteDetailToLlama(noteDetailInfo)
    }

    private fun sendNoteDetailToLlama(noteDetailInfo: NoteDetailInfo) {
        restTemplateRequester.sendNoteDetail(noteDetailInfo.id, noteDetailInfo.userId, noteDetailInfo.scripts)
    }

    fun getNoteDetail(getNoteDetailCriterion: GetNoteDetailCriterion): NoteDetailInfo {
        return noteService.getNoteDetail(getNoteDetailCriterion)
    }

    fun getNoteOverviewList(getNoteOverviewListCriterion: GetNoteOverviewListCriterion): NoteOverviewListInfo {
        return noteService.getNoteOverviewList(getNoteOverviewListCriterion)
    }

    fun updateNoteErrorStatus(updateErrorStatusCommand: UpdateErrorStatusCommand) {
        noteService.updateNoteErrorStatus(updateErrorStatusCommand)
    }

    fun updateTitle(updateTitleCommand: UpdateTitleCommand): NoteInfo = noteService.updateTitle(updateTitleCommand)

    fun updateScript(updateScriptCommand: UpdateScriptCommand): ScriptInfo =
        noteService.updateScript(updateScriptCommand)

    fun updateMemo(updateMemoCommand: UpdateMemoCommand): MemoInfo = noteService.updateMemo(updateMemoCommand)
}
