package com.team983.synnote.domain.note.interfaces.dto

data class AsrRequestResponse(
    val noteId: Long,
    val filename: String,
    val recordingDuration: Long
)

data class AsrResultResponse(
    val noteId: Long,
    val texts: List<String>
)
