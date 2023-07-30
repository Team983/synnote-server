package com.team983.synnote.domain.note.interfaces.controller

import com.team983.synnote.common.dto.BaseResponse
import com.team983.synnote.domain.note.applications.NoteFacade
import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.interfaces.dto.CreateNoteRequest
import com.team983.synnote.domain.note.interfaces.dto.NoteResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/note")
@RestController
class NoteController(
    private val noteFacade: NoteFacade
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createNote(
        @RequestBody
        @Valid
        createNoteRequest: CreateNoteRequest
    ): BaseResponse<NoteResponse> {
        val createNoteCommand = CreateNoteCommand("adasd", createNoteRequest)
        val noteResponse = NoteResponse(noteFacade.createNote(createNoteCommand))
        return BaseResponse(data = noteResponse)
    }
}
