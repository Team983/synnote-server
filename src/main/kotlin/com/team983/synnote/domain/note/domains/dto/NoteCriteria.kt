package com.team983.synnote.domain.note.domains.dto

import org.springframework.data.domain.PageRequest

data class GetNoteDetailCriterion(
    val userId: String,
    val noteId: Long
)

data class GetNoteOverviewListCriterion(
    val userId: String,
    val pageable: PageRequest
)

data class GetSummaryCriterion(
    val userId: String,
    val noteId: Long
)
