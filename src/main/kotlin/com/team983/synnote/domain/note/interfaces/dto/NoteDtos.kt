package com.team983.synnote.domain.note.interfaces.dto

import com.example.demo.common.annotation.ValidEnum
import com.fasterxml.jackson.annotation.JsonProperty
import com.team983.synnote.domain.note.domains.dto.NoteDetailInfo
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import com.team983.synnote.domain.note.domains.dto.NoteRecordingInfo
import com.team983.synnote.domain.note.domains.dto.RecordingInfo
import com.team983.synnote.domain.note.domains.dto.ScriptInfo
import com.team983.synnote.domain.note.domains.enums.DomainType
import com.team983.synnote.domain.note.domains.enums.Status
import com.team983.synnote.domain.note.domains.enums.UploadType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class CreateNoteRequest(
    @field:NotBlank
    @field:ValidEnum(enumClass = DomainType::class, message = "IT, GENERAL 중 하나를 선택해주세요.")
    @JsonProperty("domain")
    private val _domain: String?,

    @field:NotBlank
    @field:ValidEnum(enumClass = Status::class, message = "RECORDING, PROCESSING, DONE 중 하나를 선택해주세요.")
    @JsonProperty("status")
    private val _status: String?,

    @field:NotBlank
    @field:ValidEnum(enumClass = UploadType::class, message = "RECORDING, FILE 중 하나를 선택해주세요.")
    @JsonProperty("uploadType")
    private val _uploadType: String?
) {
    val domainType: DomainType
        get() = DomainType.valueOf(_domain!!)

    val status: Status
        get() = Status.valueOf(_status!!)

    val uploadType: UploadType
        get() = UploadType.valueOf(_uploadType!!)
}

data class NoteResponse(
    val noteId: Long,
    val title: String,
    val domain: DomainType,
    val status: Status,
    val uploadType: UploadType,
    val deletedFlag: Boolean,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime
) {
    constructor(noteInfo: NoteInfo) : this(
        noteId = noteInfo.id,
        title = noteInfo.title,
        domain = noteInfo.domainType,
        status = noteInfo.status,
        uploadType = noteInfo.uploadType,
        deletedFlag = noteInfo.deletedFlag,
        createdDate = noteInfo.createdDate,
        updatedDate = noteInfo.updatedDate
    )
}

data class EndRecordingRequest(
    @field:NotNull
    @JsonProperty("noteId")
    private val _noteId: Long?,

    @field:NotBlank
    @JsonProperty("encodedFileName")
    private val _encodedFileName: String?,

    @field:NotBlank
    @JsonProperty("s3ObjectUrl")
    private val _s3ObjectUrl: String?
) {
    val noteId: Long
        get() = _noteId!!

    val encodedFileName: String
        get() = _encodedFileName!!

    val s3ObjectUrl: String
        get() = _s3ObjectUrl!!
}

data class NoteRecordingResponse(
    val noteId: Long,
    val title: String,
    val domain: String,
    val status: String,
    val uploadType: String,
    val deletedFlag: Boolean,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime,
    val recording: RecordingInfo
) {
    constructor(noteRecordingInfo: NoteRecordingInfo) : this(
        noteId = noteRecordingInfo.id,
        title = noteRecordingInfo.title,
        domain = noteRecordingInfo.domainType.name,
        status = noteRecordingInfo.status.name,
        uploadType = noteRecordingInfo.uploadType.name,
        deletedFlag = noteRecordingInfo.deletedFlag,
        createdDate = noteRecordingInfo.createdDate,
        updatedDate = noteRecordingInfo.updatedDate,
        recording = noteRecordingInfo.recording
    )
}

data class NoteDetailResponse(
    val noteId: Long,
    val title: String,
    val domain: String,
    val status: String,
    val uploadType: String,
    val deletedFlag: Boolean,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime,
    val recording: RecordingInfo,
    val scriptList: List<ScriptInfo>
) {
    constructor(noteDetailInfo: NoteDetailInfo) : this(
        noteId = noteDetailInfo.id,
        title = noteDetailInfo.title,
        domain = noteDetailInfo.domainType.name,
        status = noteDetailInfo.status.name,
        uploadType = noteDetailInfo.uploadType.name,
        deletedFlag = noteDetailInfo.deletedFlag,
        createdDate = noteDetailInfo.createdDate,
        updatedDate = noteDetailInfo.updatedDate,
        recording = noteDetailInfo.recording,
        scriptList = noteDetailInfo.scripts
    )
}
