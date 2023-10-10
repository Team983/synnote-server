package com.team983.synnote.domain.note.domains.service

import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.DeleteNoteCommand
import com.team983.synnote.domain.note.domains.dto.EndRecordingCommand
import com.team983.synnote.domain.note.domains.dto.GetNoteDetailCriterion
import com.team983.synnote.domain.note.domains.dto.GetNoteOverviewListCriterion
import com.team983.synnote.domain.note.domains.dto.NoteDetailInfo
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import com.team983.synnote.domain.note.domains.dto.NoteOverviewListInfo
import com.team983.synnote.domain.note.domains.dto.NoteRecordingInfo
import com.team983.synnote.domain.note.domains.dto.SaveFullScriptCommand
import com.team983.synnote.domain.note.domains.dto.UpdateTitleCommand
import com.team983.synnote.domain.note.interfaces.dto.AsrRequestResponse

interface NoteService {
    fun createNote(createNoteCommand: CreateNoteCommand): NoteInfo

    fun deleteNote(deleteNoteCommand: DeleteNoteCommand): NoteInfo

    fun attachRecording(
        asrRequestResponse: AsrRequestResponse,
        endRecordingCommand: EndRecordingCommand
    ): NoteRecordingInfo

    fun saveScript(saveFullScriptCommand: SaveFullScriptCommand)

    fun getNoteDetail(getNoteDetailCriterion: GetNoteDetailCriterion): NoteDetailInfo

    fun getNoteOverviewList(getNoteOverviewListCriterion: GetNoteOverviewListCriterion): NoteOverviewListInfo

    fun hasNoteNoRecording(endRecordingCommand: EndRecordingCommand)

    fun updateNoteErrorStatus(noteId: Long)

    fun updateTitle(updateTitleCommand: UpdateTitleCommand): NoteInfo
}
