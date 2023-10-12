package com.team983.synnote.domain.note.domains.dto

import com.team983.synnote.domain.note.domains.entity.Note
import com.team983.synnote.domain.note.domains.entity.Script
import com.team983.synnote.domain.note.domains.enums.AsrType
import com.team983.synnote.domain.note.domains.enums.DomainType
import com.team983.synnote.domain.note.domains.enums.Status
import com.team983.synnote.domain.note.domains.enums.UploadType
import com.team983.synnote.domain.note.interfaces.dto.AsrErrorResponse
import com.team983.synnote.domain.note.interfaces.dto.AsrResultResponse
import com.team983.synnote.domain.note.interfaces.dto.CreateNoteRequest
import com.team983.synnote.domain.note.interfaces.dto.EndRecordingRequest
import com.team983.synnote.domain.note.interfaces.dto.Segment
import com.team983.synnote.domain.note.interfaces.dto.UpdateTitleRequest
import com.team983.synnote.domain.note.interfaces.dto.WhisperxAsrResultResponse

data class CreateNoteCommand(
    val userId: String,
    val domainType: DomainType,
    val status: Status,
    val uploadType: UploadType
) {
    constructor(userId: String, domainType: CreateNoteRequest) : this(
        userId = userId,
        domainType = domainType.domainType,
        status = domainType.status,
        uploadType = domainType.uploadType
    )

    fun toEntity(): Note {
        return Note(
            userId = userId,
            domainType = domainType,
            status = status,
            uploadType = uploadType
        )
    }
}

data class DeleteNoteCommand(
    val userId: String,
    val noteId: Long
)

data class EndRecordingCommand(
    val userId: String,
    val noteId: Long,
    val encodedFileName: String,
    val s3ObjectUrl: String
) {
    constructor(userId: String, endRecordingRequest: EndRecordingRequest) : this(
        userId = userId,
        noteId = endRecordingRequest.noteId,
        encodedFileName = endRecordingRequest.encodedFileName,
        s3ObjectUrl = endRecordingRequest.s3ObjectUrl
    )
}

abstract class BaseSaveScriptCommand(
    open val noteId: Long,
    open val asrType: AsrType = AsrType.FULL,
    open var language: String? = null
) {
    abstract fun toScripts(): List<Script>
}

data class SaveScriptCommand(
    override val noteId: Long,
    val texts: List<String>
) : BaseSaveScriptCommand(noteId) {
    constructor(asrResultResponse: AsrResultResponse) : this(
        noteId = asrResultResponse.noteId,
        texts = asrResultResponse.texts
    )

    override fun toScripts(): List<Script> {
        return texts.map { text ->
            Script(asrType, text)
        }
    }
}

data class SaveFullScriptCommand(
    override val noteId: Long,
    override var language: String?,
    val segments: List<Segment>
) : BaseSaveScriptCommand(noteId, AsrType.FULL, language) {
    constructor(whisperxAsrResultResponse: WhisperxAsrResultResponse) : this(
        noteId = whisperxAsrResultResponse.noteId,
        segments = whisperxAsrResultResponse.segments,
        language = whisperxAsrResultResponse.language
    )

    override fun toScripts(): List<Script> {
        return segments.map { segment ->
            Script(
                asrType,
                segment.text,
                segment.speaker,
                segment.startInMilliseconds,
                segment.endInMilliseconds
            )
        }
    }
}

data class UpdateTitleCommand(
    val userId: String,
    val noteId: Long,
    val title: String
) {
    constructor(userId: String, noteId: Long, updateTitleRequest: UpdateTitleRequest) : this(
        userId = userId,
        noteId = noteId,
        title = updateTitleRequest.title
    )
}

data class UpdateErrorStatusCommand(
    val noteId: Long,
    val status: Status
) {
    constructor(asrErrorResponse: AsrErrorResponse) : this(
        noteId = asrErrorResponse.noteId,
        status = asrErrorResponse.status
    )
}
