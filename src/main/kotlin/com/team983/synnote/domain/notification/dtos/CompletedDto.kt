package com.team983.synnote.domain.notification.dtos

data class CompletedDto(
    val type: String,
    val userId: String,
    val noteId: Long,
    val result: String
)
