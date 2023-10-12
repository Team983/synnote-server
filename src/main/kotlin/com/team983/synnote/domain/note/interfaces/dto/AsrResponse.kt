package com.team983.synnote.domain.note.interfaces.dto

import com.team983.synnote.domain.note.domains.entity.Recording
import com.team983.synnote.domain.note.domains.enums.Status

data class AsrRequestResponse(
    val noteId: Long,
    val filename: String,
    val duration: Long?,
    val s3ObjectUrl: String?,
    val status: Status
) {
    fun toEntity(): Recording {
        return Recording(
            s3ObjectUrl = s3ObjectUrl!!,
            recordingDuration = duration!!
        )
    }
}

data class AsrResultResponse(
    val noteId: Long,
    val texts: List<String>
)

data class WhisperxAsrResultResponse(
    val noteId: Long,
    val segments: List<Segment>,
    val language: String
)

data class Segment(
    val text: String,
    val start: Long,
    val end: Long,
    val speaker: String
) {
    val startInMilliseconds: Long
        get() = start * 1000

    val endInMilliseconds: Long
        get() = end * 1000
}

data class AsrErrorResponse(
    val noteId: Long,
    val message: String,
    val status: Status
)
