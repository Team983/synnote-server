package com.team983.synnote.domain.note.interfaces.dto

data class AsrRequestResponse(
    val filename: String,
    val recordingDuration: Long
)

data class AsrResultResponse(
    val result: List<String>
)
