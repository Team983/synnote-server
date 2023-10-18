package com.team983.synnote.domain.note.interfaces.dto

import com.example.demo.common.annotation.ValidEnum
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.team983.synnote.domain.note.domains.dto.NoteDetailInfo
import com.team983.synnote.domain.note.domains.dto.NoteDetailInfo.*
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import com.team983.synnote.domain.note.domains.dto.NoteOverviewInfo
import com.team983.synnote.domain.note.domains.dto.NoteOverviewListInfo
import com.team983.synnote.domain.note.domains.dto.NoteRecordingInfo
import com.team983.synnote.domain.note.domains.dto.RecordingInfo
import com.team983.synnote.domain.note.domains.enums.AsrType
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    val createdDate: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
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
    private val _s3ObjectUrl: String?,

    @field:NotNull
    @JsonProperty("memoList")
    private val _memoList: List<MemoRequest>?

) {
    val noteId: Long
        get() = _noteId!!

    val encodedFileName: String
        get() = _encodedFileName!!

    val s3ObjectUrl: String
        get() = _s3ObjectUrl!!

    val memoList: List<MemoRequest>
        get() = _memoList!!

    data class MemoRequest(
        @field:NotBlank
        @JsonProperty("text")
        private val _text: String?,

        @field:NotNull
        @JsonProperty("start")
        private val _start: Long?
    ) {
        val text: String
            get() = _text!!

        val start: Long
            get() = _start!!
    }
}

data class NoteRecordingResponse(
    val noteId: Long,
    val title: String,
    val domain: String,
    val status: String,
    val uploadType: String,
    val deletedFlag: Boolean,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    val createdDate: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    val updatedDate: LocalDateTime,
    val recording: RecordingResponse?
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
        recording = noteRecordingInfo.recording?.let { RecordingResponse(it) }
    )
}

data class NoteDetailResponse(
    val noteId: Long,
    val title: String,
    val domain: String,
    val status: String,
    val uploadType: String,
    val deletedFlag: Boolean,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    val createdDate: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    val updatedDate: LocalDateTime,
    val recording: RecordingResponse?,
    val scriptList: List<ScriptResponse>,
    val memoList: List<MemoResponse>
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
        recording = noteDetailInfo.recording?.let { RecordingResponse(it) },
        scriptList = noteDetailInfo.scripts.map { ScriptResponse(it) },
        memoList = noteDetailInfo.memos.map { MemoResponse(it) }
    )

    data class ScriptResponse(
        val scriptId: Long,
        val asrType: AsrType,
        val text: String,
        val speaker: String?,
        val start: Long?,
        val end: Long?
    ) {
        constructor(scriptInfo: ScriptInfo) : this(
            scriptId = scriptInfo.id,
            asrType = scriptInfo.asrType,
            text = scriptInfo.text,
            speaker = scriptInfo.speaker,
            start = scriptInfo.start,
            end = scriptInfo.end
        )
    }

    data class MemoResponse(
        val memoId: Long,
        val text: String,
        val start: Long
    ) {
        constructor(memoInfo: MemoInfo) : this(
            memoId = memoInfo.id,
            text = memoInfo.text,
            start = memoInfo.start
        )
    }
}

data class RecordingResponse(
    val s3ObjectUrl: String,
    val recordingDuration: Long
) {
    constructor(recordingInfo: RecordingInfo) : this(
        s3ObjectUrl = recordingInfo.s3ObjectUrl,
        recordingDuration = recordingInfo.recordingDuration
    )
}

data class NoteOverviewListResponse(
    val noteList: List<NoteOverviewResponse>,
    val hasNext: Boolean
) {
    companion object {
        fun fromNoteOverviewListInfo(noteOverviewListInfo: NoteOverviewListInfo): NoteOverviewListResponse {
            val map = noteOverviewListInfo.noteOverviewInfos.map { NoteOverviewResponse(it) }
            return NoteOverviewListResponse(map, noteOverviewListInfo.hasNext)
        }
    }

    data class NoteOverviewResponse(
        val noteId: Long,
        val title: String,
        val domain: DomainType,
        val status: Status,
        val uploadType: UploadType,
        val deletedFlag: Boolean,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        val createdDate: LocalDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        val updatedDate: LocalDateTime,
        val recordingDuration: Long?,
        val firstScript: String?
    ) {
        constructor(noteOverviewInfo: NoteOverviewInfo) : this(
            noteId = noteOverviewInfo.id,
            title = noteOverviewInfo.title,
            domain = noteOverviewInfo.domainType,
            status = noteOverviewInfo.status,
            uploadType = noteOverviewInfo.uploadType,
            deletedFlag = noteOverviewInfo.deletedFlag,
            createdDate = noteOverviewInfo.createdDate,
            updatedDate = noteOverviewInfo.updatedDate,
            recordingDuration = noteOverviewInfo.recordingDuration,
            firstScript = noteOverviewInfo.firstScript
        )
    }
}

data class UpdateTitleRequest(
    @field:NotBlank
    @JsonProperty("title")
    private val _title: String?
) {
    val title: String
        get() = _title!!
}

data class UpdateScriptRequest(
    @field:NotBlank
    @JsonProperty("text")
    private val _text: String?
) {
    val text: String
        get() = _text!!
}

data class UpdateMemoRequest(
    @field:NotBlank
    @JsonProperty("text")
    private val _text: String?
) {
    val text: String
        get() = _text!!
}
