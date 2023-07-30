package com.team983.synnote.domain.note.domains.dto

import com.team983.synnote.domain.note.domains.entity.Note
import com.team983.synnote.domain.note.domains.enums.DomainType
import com.team983.synnote.domain.note.domains.enums.Status
import com.team983.synnote.domain.note.domains.enums.UploadType
import com.team983.synnote.domain.note.interfaces.dto.CreateNoteRequest

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
