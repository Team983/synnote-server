package com.team983.synnote.domain.recording.interfaces.controller

import com.team983.synnote.common.dto.BaseResponse
import com.team983.synnote.domain.recording.applications.RecordingFacade
import com.team983.synnote.domain.recording.interfaces.dto.PresignedUrlResponse
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("api/v1/recording")
@RestController
class RecordingController(
    private val recordingFacade: RecordingFacade
) {

    @GetMapping("presigned-url/{fileName}")
    @ResponseStatus(OK)
    fun getPresignedUrl(
        @PathVariable fileName: String
    ): BaseResponse<PresignedUrlResponse> = BaseResponse(data = recordingFacade.getPresignedUrl(fileName))
}
