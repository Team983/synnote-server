package com.team983.synnote.domain.note.domains.service

import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.DeleteNoteCommand
import com.team983.synnote.domain.note.domains.dto.EndRecordingCommand
import com.team983.synnote.domain.note.domains.dto.GetNoteDetailCommand
import com.team983.synnote.domain.note.domains.dto.GetNoteOverviewListCommand
import com.team983.synnote.domain.note.domains.dto.NoteDetailInfo
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import com.team983.synnote.domain.note.domains.dto.NoteOverviewInfo
import com.team983.synnote.domain.note.domains.dto.NoteRecordingInfo
import com.team983.synnote.domain.note.domains.dto.SaveFullScriptCommand

interface NoteService {
    fun createNote(createNoteCommand: CreateNoteCommand): NoteInfo

    fun deleteNote(deleteNoteCommand: DeleteNoteCommand): NoteInfo

    fun attachRecording(endRecordingCommand: EndRecordingCommand): NoteRecordingInfo

    fun saveScript(saveFullScriptCommand: SaveFullScriptCommand)

    fun getNoteDetail(noteDetailCommand: GetNoteDetailCommand): NoteDetailInfo

    fun getNoteOverviewList(getNoteOverviewListCommand: GetNoteOverviewListCommand): List<NoteOverviewInfo>
}
