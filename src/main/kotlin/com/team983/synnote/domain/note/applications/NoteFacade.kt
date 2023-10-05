package com.team983.synnote.domain.note.applications

import com.team983.synnote.common.exception.EntityNotFoundException
import com.team983.synnote.common.status.ResultCode.*
import com.team983.synnote.common.utils.RestTemplateRequester
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
        val asrRequestResponse = sendRequestAsrService(endRecordingCommand)
        return noteService.attachRecording(asrRequestResponse, endRecordingCommand)
    }

    private fun sendRequestAsrService(endRecordingCommand: EndRecordingCommand): AsrRequestResponse {
        return restTemplateRequester.sendRequest(
            "http://172.20.245.195:8081/asr" +
                "/${endRecordingCommand.noteId}?filename=${endRecordingCommand.encodedFileName}",
            AsrRequestResponse::class.java
        )
    }

    fun saveScript(saveFullScriptCommand: SaveFullScriptCommand) {
        noteService.saveScript(saveFullScriptCommand)
    }

    fun getNoteDetail(getNoteDetailCriterion: GetNoteDetailCriterion): NoteDetailInfo {
        return noteService.getNoteDetail(getNoteDetailCriterion)
    }

    fun getNoteOverviewList(getNoteOverviewListCriterion: GetNoteOverviewListCriterion): List<NoteOverviewInfo> {
        return noteService.getNoteOverviewList(getNoteOverviewListCriterion)
    }

    fun updateNoteErrorStatus(noteId: Long) {
        noteService.updateNoteErrorStatus(noteId)
    }

    fun updateTitle(updateTitleCommand: UpdateTitleCommand): NoteInfo = noteService.updateTitle(updateTitleCommand)
}
