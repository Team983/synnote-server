package com.team983.synnote.domain.recording.domain

import com.team983.synnote.domain.recording.interfaces.dto.PresignedUrlResponse

interface RecordingService {

    fun getPresignedUrl(fileName: String): PresignedUrlResponse
}
