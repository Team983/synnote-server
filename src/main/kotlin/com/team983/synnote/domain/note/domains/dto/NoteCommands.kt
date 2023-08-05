package com.team983.synnote.domain.note.domains.dto

import com.team983.synnote.domain.note.domains.entity.Note
import com.team983.synnote.domain.note.domains.entity.Recording
import com.team983.synnote.domain.note.domains.entity.Script
import com.team983.synnote.domain.note.domains.enums.AsrType
import com.team983.synnote.domain.note.domains.enums.DomainType
import com.team983.synnote.domain.note.domains.enums.Status
import com.team983.synnote.domain.note.domains.enums.UploadType
import com.team983.synnote.domain.note.interfaces.dto.AsrResultResponse
import com.team983.synnote.domain.note.interfaces.dto.CreateNoteRequest
import com.team983.synnote.domain.note.interfaces.dto.EndRecordingRequest

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
    var recordingDuration: Long? = null
        protected set

    constructor(userId: String, endRecordingRequest: EndRecordingRequest) : this(
        userId = userId,
        noteId = endRecordingRequest.noteId,
        encodedFileName = endRecordingRequest.encodedFileName,
        s3ObjectUrl = endRecordingRequest.s3ObjectUrl
    )

    fun toEntity(): Recording {
        return Recording(
            s3ObjectUrl = s3ObjectUrl,
            recordingDuration = recordingDuration!!
        )
    }

    fun setRecordingDuration(recordingDuration: Long) {
        this.recordingDuration = recordingDuration
    }
}

data class SaveFullScriptCommand(
    val noteId: Long,
    val texts: List<String>,
    val asrType: AsrType = AsrType.FULL
) {
    constructor(noteId: AsrResultResponse) : this(
        noteId = noteId.noteId,
        texts = noteId.texts
    )

    fun toScripts(): List<Script> {
        return texts.map { text ->
            Script(asrType, text)
        }
    }
}

data class GetNoteDetailCommand(
    val userId: String,
    val noteId: Long
)
