package com.team983.synnote.domain.note.interfaces.controller

import com.team983.synnote.common.authority.decodeJwt
import com.team983.synnote.common.dto.BaseResponse
import com.team983.synnote.domain.note.applications.NoteFacade
import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.CreateSummaryCommand
import com.team983.synnote.domain.note.domains.dto.DeleteNoteCommand
import com.team983.synnote.domain.note.domains.dto.EndRecordingCommand
import com.team983.synnote.domain.note.domains.dto.GetNoteDetailCriterion
import com.team983.synnote.domain.note.domains.dto.GetNoteOverviewListCriterion
import com.team983.synnote.domain.note.domains.dto.UpdateMemoCommand
import com.team983.synnote.domain.note.domains.dto.UpdateScriptCommand
import com.team983.synnote.domain.note.domains.dto.UpdateTitleCommand
import com.team983.synnote.domain.note.interfaces.dto.CreateNoteRequest
import com.team983.synnote.domain.note.interfaces.dto.EndRecordingRequest
import com.team983.synnote.domain.note.interfaces.dto.NoteResponse
import com.team983.synnote.domain.note.interfaces.dto.NoteDetailResponse
import com.team983.synnote.domain.note.interfaces.dto.NoteDetailResponse.MemoResponse
import com.team983.synnote.domain.note.interfaces.dto.NoteDetailResponse.ScriptResponse
import com.team983.synnote.domain.note.interfaces.dto.NoteOverviewListResponse
import com.team983.synnote.domain.note.interfaces.dto.NoteRecordingResponse
import com.team983.synnote.domain.note.interfaces.dto.UpdateMemoRequest
import com.team983.synnote.domain.note.interfaces.dto.UpdateScriptRequest
import com.team983.synnote.domain.note.interfaces.dto.UpdateTitleRequest
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
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

    @GetMapping("/api/v1/note/{noteId}")
    fun getNoteDetail(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String,
        @PathVariable("noteId") noteId: Long
    ): BaseResponse<NoteDetailResponse> {
        val getNoteDetailCriterion = GetNoteDetailCriterion(decodeJwt(encodedJwt).sub, noteId)
        val noteDetailResponse = NoteDetailResponse(noteFacade.getNoteDetail(getNoteDetailCriterion))
        return BaseResponse(data = noteDetailResponse)
    }

    @GetMapping("/api/v1/note-list")
    fun getNoteOverviewList(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String,
        @RequestParam("start-index") startIndex: Int,
        @RequestParam("count") count: Int
    ): BaseResponse<NoteOverviewListResponse> {
        val pageable = PageRequest.of(startIndex, count, Sort.by("createdDate").descending())
        val getNoteOverviewListCriterion = GetNoteOverviewListCriterion(decodeJwt(encodedJwt).sub, pageable)
        val noteOverviewResponse = NoteOverviewListResponse.fromNoteOverviewListInfo(
            noteFacade.getNoteOverviewList(getNoteOverviewListCriterion)
        )
        return BaseResponse(data = noteOverviewResponse)
    }

    @PatchMapping("/api/v1/note/{noteId}/title")
    fun updateTitle(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String,
        @PathVariable("noteId") noteId: Long,
        @RequestBody
        @Valid
        updateTitleRequest: UpdateTitleRequest
    ): BaseResponse<NoteResponse> {
        val updateTitleCommand = UpdateTitleCommand(decodeJwt(encodedJwt).sub, noteId, updateTitleRequest)
        val noteResponse = NoteResponse(noteFacade.updateTitle(updateTitleCommand))
        return BaseResponse(data = noteResponse)
    }

    @PatchMapping("/api/v1/note/{noteId}/script/{scriptId}")
    fun updateScript(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String,
        @PathVariable("noteId") noteId: Long,
        @PathVariable("scriptId") scriptId: Long,
        @RequestBody
        @Valid
        updateScriptRequest: UpdateScriptRequest
    ): BaseResponse<ScriptResponse> {
        val updateScriptCommand = UpdateScriptCommand(decodeJwt(encodedJwt).sub, noteId, scriptId, updateScriptRequest)
        val scriptResponse = ScriptResponse(noteFacade.updateScript(updateScriptCommand))
        return BaseResponse(data = scriptResponse)
    }

    @PatchMapping("/api/v1/note/{noteId}/memo/{memoId}")
    fun updateMemo(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String,
        @PathVariable("noteId") noteId: Long,
        @PathVariable("memoId") memoId: Long,
        @RequestBody
        @Valid
        updateMemoRequest: UpdateMemoRequest
    ): BaseResponse<MemoResponse> {
        val updateMemoCommand = UpdateMemoCommand(decodeJwt(encodedJwt).sub, noteId, memoId, updateMemoRequest)
        val memoResponse = MemoResponse(noteFacade.updateMemo(updateMemoCommand))
        return BaseResponse(data = memoResponse)
    }

    @PostMapping("/api/v1/note/{noteId}/summary")
    fun createSummary(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String,
        @PathVariable("noteId") noteId: Long
    ) {
        noteFacade.createSummary(CreateSummaryCommand(decodeJwt(encodedJwt).sub, noteId))
    }
}
