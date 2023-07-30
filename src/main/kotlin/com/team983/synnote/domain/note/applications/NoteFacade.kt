package com.team983.synnote.domain.note.applications

import com.team983.synnote.common.exception.EntityNotFoundException
import com.team983.synnote.common.status.ResultCode.*
import com.team983.synnote.domain.note.domains.dto.CreateNoteCommand
import com.team983.synnote.domain.note.domains.dto.NoteInfo
import com.team983.synnote.domain.note.domains.service.NoteService
import com.team983.synnote.domain.user.domains.service.UserService
import org.springframework.stereotype.Service

@Service
class NoteFacade(
    private val noteService: NoteService,
    private val userService: UserService
) {
    fun createNote(createNoteCommand: CreateNoteCommand): NoteInfo {
        userService.isRegisteredUser(createNoteCommand.userId).takeIf { it }
            ?: throw EntityNotFoundException(USER_NOT_FOUND)
        return noteService.createNote(createNoteCommand)
    }
}
