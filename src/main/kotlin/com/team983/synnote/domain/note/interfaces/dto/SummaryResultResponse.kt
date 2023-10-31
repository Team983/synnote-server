package com.team983.synnote.domain.note.interfaces.dto

// 요약 결과에 대한 응답
data class SummaryResultResponse(
    val userId: String,
    val noteId: Long,
    val text: String,
    val status: String
)
