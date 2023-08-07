package com.team983.synnote.domain.note.interfaces.listener

import com.team983.synnote.domain.note.applications.NoteFacade
import com.team983.synnote.domain.note.domains.dto.SaveFullScriptCommand
import com.team983.synnote.domain.note.interfaces.dto.AsrResultResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AsrCompletedListener(
    private val noteFacade: NoteFacade
) {

    @PostMapping("/api/v1/note/asr-completed")
    fun completeAsr(
        @RequestBody
        asrResultResponse: AsrResultResponse
    ) {
        noteFacade.saveScript(SaveFullScriptCommand(asrResultResponse))
    }
}