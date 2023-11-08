package com.team983.synnote.domain.note.interfaces.listener

import com.team983.synnote.domain.note.applications.NoteFacade
import com.team983.synnote.domain.note.domains.dto.SaveQueryCommand
import com.team983.synnote.domain.note.interfaces.dto.QueryResultResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class QueryCompletedListener(
    private val noteFacade: NoteFacade
) {
    @PostMapping("/api/v1/note/query-completed")
    fun completeQuery(
        @RequestBody
        queryResultResponse: QueryResultResponse
    ) {
        noteFacade.saveQuery(SaveQueryCommand(queryResultResponse))
    }
}
