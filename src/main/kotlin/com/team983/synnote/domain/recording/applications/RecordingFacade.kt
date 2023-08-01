package com.team983.synnote.domain.recording.applications

import com.team983.synnote.domain.recording.domain.RecordingService
import com.team983.synnote.domain.recording.interfaces.dto.PresignedUrlResponse
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class RecordingFacade(
    private val recordingService: RecordingService
) {
    fun getPresignedUrl(fileName: String): PresignedUrlResponse = recordingService.getPresignedUrl(fileName)
}
