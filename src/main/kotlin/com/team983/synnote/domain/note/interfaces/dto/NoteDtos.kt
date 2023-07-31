package com.team983.synnote.domain.note.interfaces.dto

import com.example.demo.common.annotation.ValidEnum
import com.fasterxml.jackson.annotation.JsonProperty
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import com.team983.synnote.domain.note.domains.enums.DomainType
import com.team983.synnote.domain.note.domains.enums.Status
import com.team983.synnote.domain.note.domains.enums.UploadType
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class CreateNoteRequest(
    @field:NotBlank
    @field:ValidEnum(enumClass = DomainType::class, message = "IT, GENERAL 중 하나를 선택해주세요.")
    @JsonProperty("domain")
    private val _domain: String,

    @field:NotBlank
    @field:ValidEnum(enumClass = Status::class, message = "RECORDING, PROCESSING, DONE 중 하나를 선택해주세요.")
    @JsonProperty("status")
    private val _status: String,

    @field:NotBlank
    @field:ValidEnum(enumClass = UploadType::class, message = "RECORDING, FILE 중 하나를 선택해주세요.")
    @JsonProperty("uploadType")
    private val _uploadType: String
) {
    val domainType: DomainType
        get() = DomainType.valueOf(_domain)

    val status: Status
        get() = Status.valueOf(_status)

    val uploadType: UploadType
        get() = UploadType.valueOf(_uploadType)
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