package com.team983.synnote.domain.note.interfaces.dto

// 사용자 Query 생성에 대한 응답
data class QueryResultResponse(
    val userId: String,
    val noteId: Long,
    val text: String,
    val status: String
)
