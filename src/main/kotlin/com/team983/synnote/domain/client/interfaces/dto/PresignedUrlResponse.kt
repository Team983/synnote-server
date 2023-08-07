package com.team983.synnote.domain.client.interfaces.dto

import java.net.URL

data class PresignedUrlResponse(
    val preSignedUrl: URL,
    val encodedFileName: String
)
