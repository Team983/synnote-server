package com.team983.synnote.domain.note.domains.dto

import com.team983.synnote.domain.note.domains.entity.Memo
import com.team983.synnote.domain.note.domains.entity.Note
import com.team983.synnote.domain.note.domains.entity.Recording
import com.team983.synnote.domain.note.domains.entity.Script
import com.team983.synnote.domain.note.domains.entity.Summary
import com.team983.synnote.domain.note.domains.enums.AsrType
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

data class NoteRecordingInfo(
    val id: Long,
    val title: String,
    val domainType: DomainType,
    val status: Status,
    val uploadType: UploadType,
    val deletedFlag: Boolean,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime,
    val recording: RecordingInfo?
) {
    constructor(note: Note) : this(
        id = note.id!!,
        title = note.title,
        domainType = note.domainType,
        status = note.status,
        uploadType = note.uploadType,
        deletedFlag = note.deletedFlag,
        createdDate = note.createdDate!!,
        updatedDate = note.updatedDate!!,
        recording = note.recording?.let { RecordingInfo(it) }
    )
}

data class RecordingInfo(
    val s3ObjectUrl: String,
    val recordingDuration: Long
) {
    constructor(recording: Recording) : this(
        s3ObjectUrl = recording.s3ObjectUrl,
        recordingDuration = recording.recordingDuration
    )
}

data class NoteDetailInfo(
    val id: Long,
    val userId: String,
    val title: String,
    val domainType: DomainType,
    val status: Status,
    val uploadType: UploadType,
    val deletedFlag: Boolean,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime,
    val recording: RecordingInfo?,
    val scripts: List<ScriptInfo>,
    val memos: List<MemoInfo>
) {
    constructor(note: Note) : this(
        id = note.id!!,
        userId = note.userId,
        title = note.title,
        domainType = note.domainType,
        status = note.status,
        uploadType = note.uploadType,
        deletedFlag = note.deletedFlag,
        createdDate = note.createdDate!!,
        updatedDate = note.updatedDate!!,
        recording = note.recording?.let { RecordingInfo(it) },
        scripts = note.scripts.map { ScriptInfo(it) },
        memos = note.memos.map { MemoInfo(it) }
    )

    data class ScriptInfo(
        val id: Long,
        val asrType: AsrType,
        val text: String,
        val speaker: String?,
        val start: Long?,
        val end: Long?
    ) {
        constructor(script: Script) : this(
            id = script.id!!,
            asrType = script.asrType,
            text = script.text,
            speaker = script.speaker,
            start = script.start,
            end = script.end
        )
    }

    data class MemoInfo(
        val id: Long,
        val text: String,
        val start: Long
    ) {
        constructor(memo: Memo) : this(
            id = memo.id!!,
            text = memo.text,
            start = memo.start
        )
    }
}

data class NoteOverviewInfo(
    val id: Long,
    val title: String,
    val domainType: DomainType,
    val status: Status,
    val uploadType: UploadType,
    val deletedFlag: Boolean,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime,
    val recordingDuration: Long?,
    val firstScript: String?
) {
    constructor(note: Note) : this(
        id = note.id!!,
        title = note.title,
        domainType = note.domainType,
        status = note.status,
        uploadType = note.uploadType,
        deletedFlag = note.deletedFlag,
        createdDate = note.createdDate!!,
        updatedDate = note.updatedDate!!,
        recordingDuration = note.recording?.recordingDuration,
        firstScript = note.scripts.firstOrNull()?.text
    )
}

data class NoteOverviewListInfo(val noteOverviewInfos: List<NoteOverviewInfo>, val hasNext: Boolean)

data class SummaryInfo(
    val id: Long,
    val text: String,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime
) {
    constructor(summary: Summary) : this(
        id = summary.id!!,
        text = summary.text,
        createdDate = summary.createdDate!!,
        updatedDate = summary.updatedDate!!
    )
}

data class SummaryListInfo(val noteId: Long, val summaryInfos: List<SummaryInfo>)
