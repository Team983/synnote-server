package com.team983.synnote.domain.note.interfaces.listener

import com.team983.synnote.common.utils.logger
import com.team983.synnote.domain.note.applications.NoteFacade
import com.team983.synnote.domain.note.domains.dto.SaveFullScriptCommand
import com.team983.synnote.domain.note.domains.dto.SaveScriptCommand
import com.team983.synnote.domain.note.domains.dto.UpdateErrorStatusCommand
import com.team983.synnote.domain.note.interfaces.dto.AsrErrorResponse
import com.team983.synnote.domain.note.interfaces.dto.AsrResultResponse
import com.team983.synnote.domain.note.interfaces.dto.WhisperxAsrResultResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AsrCompletedListener(
    private val noteFacade: NoteFacade
) {
    val log = logger()

    @PostMapping("/api/v1/note/asr-completed")
    fun completeAsr(
        @RequestBody
        asrResultResponse: AsrResultResponse
    ) {
        noteFacade.saveScript(SaveScriptCommand(asrResultResponse))
    }

    @PostMapping("/api/v1/note/whisperx-asr-completed")
    fun completeWhisperxAsr(
        @RequestBody
        whisperxAsrResultResponse: WhisperxAsrResultResponse
    ) {
        noteFacade.saveScript(SaveFullScriptCommand(whisperxAsrResultResponse))
    }

    @PostMapping("/api/v1/note/asr-error")
    fun errorAsr(
        @RequestBody
        asrErrorResponse: AsrErrorResponse
    ) {
        log.error("ASR ERROR: ${asrErrorResponse.message}")
        noteFacade.updateNoteErrorStatus(UpdateErrorStatusCommand(asrErrorResponse))
    }
}
