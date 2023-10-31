package com.team983.synnote.domain.notification.dtos

data class SummaryCompletedDto(
    val userId: String,
    val noteId: Long,
    val message: String = "Summary completed"
)
