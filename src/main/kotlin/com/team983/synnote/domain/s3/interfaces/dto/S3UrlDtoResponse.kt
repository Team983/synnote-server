package com.team983.synnote.domain.s3.interfaces.dto

import java.net.URL

data class S3UrlDtoResponse(
    val preSignedUrl: URL,
    val encodedFileName: String
)
