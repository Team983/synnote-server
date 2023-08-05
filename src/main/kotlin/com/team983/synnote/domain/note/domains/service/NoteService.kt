package com.team983.synnote.domain.note.domains.service

import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.DeleteNoteCommand
import com.team983.synnote.domain.note.domains.dto.EndRecordingCommand
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import com.team983.synnote.domain.note.domains.dto.NoteRecordingInfo
import com.team983.synnote.domain.note.domains.dto.SaveFullScriptCommand

interface NoteService {
    fun createNote(createNoteCommand: CreateNoteCommand): NoteInfo

    fun deleteNote(deleteNoteCommand: DeleteNoteCommand): NoteInfo

    fun attachRecording(endRecordingCommand: EndRecordingCommand): NoteRecordingInfo

    fun saveScript(saveFullScriptCommand: SaveFullScriptCommand)
}
