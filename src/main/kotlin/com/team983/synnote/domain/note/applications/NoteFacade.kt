package com.team983.synnote.domain.note.applications

import com.team983.synnote.common.exception.EntityNotFoundException
import com.team983.synnote.common.status.ResultCode.*
import com.team983.synnote.common.utils.RestTemplateRequester
import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.DeleteNoteCommand
import com.team983.synnote.domain.note.domains.dto.EndRecordingCommand
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import com.team983.synnote.domain.note.domains.dto.NoteRecordingInfo
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
        val asrRequestResponse = sendRequestAsrService(endRecordingCommand)
        endRecordingCommand.setRecordingDuration(asrRequestResponse.recordingDuration)
        return noteService.attachRecording(endRecordingCommand)
    }

    private fun sendRequestAsrService(endRecordingCommand: EndRecordingCommand): AsrRequestResponse {
        return restTemplateRequester.sendRequest(
            //"http://172.20.245.195:8081/asr/${endRecordingCommand.encodedFileName}",
            "http://localhost:8000/asr/${endRecordingCommand.encodedFileName}",
            AsrRequestResponse::class.java
        )
    }
}
