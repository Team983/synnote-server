package com.team983.synnote.domain.note.domains.dto

import com.team983.synnote.domain.note.domains.entity.Note
import com.team983.synnote.domain.note.domains.enums.DomainType
import com.team983.synnote.domain.note.domains.enums.Status
import com.team983.synnote.domain.note.domains.enums.UploadType
import java.time.LocalDateTime

data class NoteInfo(
    val id: Long,
    val title: String,
    val domainType: DomainType,
    val status: Status,
    val uploadType: UploadType,
    val deletedFlag: Boolean,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime
) {
    constructor(note: Note) : this(
        id = note.id!!,
        title = note.title,
        domainType = note.domainType,
        status = note.status,
        uploadType = note.uploadType,
        deletedFlag = note.deletedFlag,
        createdDate = note.createdDate!!,
        updatedDate = note.updatedDate!!
    )
}
