package com.team983.synnote.domain.note.interfaces.listener

import com.team983.synnote.domain.note.applications.NoteFacade
import com.team983.synnote.domain.note.domains.dto.SaveSummaryCommand
import com.team983.synnote.domain.note.interfaces.dto.SummaryResultResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SummaryCompletedListener(
    private val noteFacade: NoteFacade
) {
    @PostMapping("/api/v1/note/summary-completed")
    fun completeSummary(
        @RequestBody
        summaryResultResponse: SummaryResultResponse
    ) {
        noteFacade.saveSummary(SaveSummaryCommand(summaryResultResponse))
    }
}
