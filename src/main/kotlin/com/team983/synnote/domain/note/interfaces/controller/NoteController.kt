package com.team983.synnote.domain.note.interfaces.controller

import com.team983.synnote.common.authority.decodeJwt
import com.team983.synnote.common.dto.BaseResponse
import com.team983.synnote.domain.note.applications.NoteFacade
import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.DeleteNoteCommand
import com.team983.synnote.domain.note.domains.dto.EndRecordingCommand
import com.team983.synnote.domain.note.domains.dto.GetNoteDetailCommand
import com.team983.synnote.domain.note.domains.dto.SaveFullScriptCommand
import com.team983.synnote.domain.note.interfaces.dto.CreateNoteRequest
import com.team983.synnote.domain.note.interfaces.dto.EndRecordingRequest
import com.team983.synnote.domain.note.interfaces.dto.NoteResponse
import com.team983.synnote.domain.note.interfaces.dto.AsrResultResponse
import com.team983.synnote.domain.note.interfaces.dto.NoteDetailResponse
import com.team983.synnote.domain.note.interfaces.dto.NoteRecordingResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class NoteController(
    private val noteFacade: NoteFacade
) {

    @PostMapping("/api/v1/note")
    @ResponseStatus(HttpStatus.CREATED)
    fun createNote(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String,
        @RequestBody
        @Valid
        createNoteRequest: CreateNoteRequest
    ): BaseResponse<NoteResponse> {
        val createNoteCommand = CreateNoteCommand(decodeJwt(encodedJwt).sub, createNoteRequest)
        val noteResponse = NoteResponse(noteFacade.createNote(createNoteCommand))
        return BaseResponse(data = noteResponse)
    }

    @DeleteMapping("/api/v1/note/{noteId}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteNote(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String,
        @PathVariable("noteId") noteId: Long
    ): BaseResponse<NoteResponse> {
        val deleteNoteCommand = DeleteNoteCommand(decodeJwt(encodedJwt).sub, noteId)
        val noteResponse = NoteResponse(noteFacade.deleteNote(deleteNoteCommand))
        return BaseResponse(data = noteResponse)
    }

    @PostMapping("/api/v1/note/recording-end")
    fun endRecordingAndRequestAsr(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String,
        @RequestBody
        @Valid
        endRecordingRequest: EndRecordingRequest
    ): BaseResponse<NoteRecordingResponse> {
        val endRecordingCommand = EndRecordingCommand(decodeJwt(encodedJwt).sub, endRecordingRequest)
        val noteRecordingResponse = NoteRecordingResponse(noteFacade.endRecording(endRecordingCommand))
        return BaseResponse(data = noteRecordingResponse)
    }

    @PostMapping("/api/v1/note/recording-completed")
    fun completeRecording(
        @RequestBody
        asrResultResponse: AsrResultResponse
    ) {
        noteFacade.saveScript(SaveFullScriptCommand(asrResultResponse))
    }

    @GetMapping("/api/v1/note/{noteId}")
    fun getNote(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String,
        @PathVariable("noteId") noteId: Long
    ): BaseResponse<NoteDetailResponse> {
        val getNoteDetailCommand = GetNoteDetailCommand(decodeJwt(encodedJwt).sub, noteId)
        val noteDetailResponse = NoteDetailResponse(noteFacade.getNoteDetail(getNoteDetailCommand))
        return BaseResponse(data = noteDetailResponse)
    }
}
