package com.team983.synnote.domain.note.domains.service

import com.team983.synnote.domain.note.domains.dto.BaseSaveScriptCommand
import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.DeleteNoteCommand
import com.team983.synnote.domain.note.domains.dto.EndRecordingCommand
import com.team983.synnote.domain.note.domains.dto.GetNoteDetailCriterion
import com.team983.synnote.domain.note.domains.dto.GetNoteOverviewListCriterion
import com.team983.synnote.domain.note.domains.dto.NoteDetailInfo
import com.team983.synnote.domain.note.domains.dto.NoteDetailInfo.MemoInfo
import com.team983.synnote.domain.note.domains.dto.NoteDetailInfo.ScriptInfo
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import com.team983.synnote.domain.note.domains.dto.NoteOverviewListInfo
import com.team983.synnote.domain.note.domains.dto.NoteRecordingInfo
import com.team983.synnote.domain.note.domains.dto.SaveSummaryCommand
import com.team983.synnote.domain.note.domains.dto.UpdateErrorStatusCommand
import com.team983.synnote.domain.note.domains.dto.UpdateMemoCommand
import com.team983.synnote.domain.note.domains.dto.UpdateScriptCommand
import com.team983.synnote.domain.note.domains.dto.UpdateTitleCommand
import com.team983.synnote.domain.note.domains.enums.DomainType
import com.team983.synnote.domain.note.interfaces.dto.AsrRequestResponse

interface NoteService {
    fun createNote(createNoteCommand: CreateNoteCommand): NoteInfo

    fun deleteNote(deleteNoteCommand: DeleteNoteCommand): NoteInfo

    fun attachRecordingAndMemo(
        asrRequestResponse: AsrRequestResponse,
        endRecordingCommand: EndRecordingCommand
    ): NoteRecordingInfo

    fun saveScript(saveScriptCommand: BaseSaveScriptCommand): NoteDetailInfo

    fun getNoteDetail(getNoteDetailCriterion: GetNoteDetailCriterion): NoteDetailInfo

    fun getNoteOverviewList(getNoteOverviewListCriterion: GetNoteOverviewListCriterion): NoteOverviewListInfo

    fun hasNoteNoRecording(endRecordingCommand: EndRecordingCommand)

    fun updateNoteErrorStatus(updateErrorStatusCommand: UpdateErrorStatusCommand)

    fun updateTitle(updateTitleCommand: UpdateTitleCommand): NoteInfo

    fun getDomainType(noteId: Long): DomainType

    fun updateScript(updateScriptCommand: UpdateScriptCommand): ScriptInfo

    fun updateMemo(updateMemoCommand: UpdateMemoCommand): MemoInfo

    fun saveSummary(saveSummaryCommand: SaveSummaryCommand)
}
