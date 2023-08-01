package com.team983.synnote.domain.recording.interfaces.dto

import java.net.URL

data class PresignedUrlResponse(
    val preSignedUrl: URL,
    val encodedFileName: String
)
