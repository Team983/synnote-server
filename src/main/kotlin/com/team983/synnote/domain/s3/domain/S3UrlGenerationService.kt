package com.team983.synnote.domain.s3.domain

import com.team983.synnote.domain.s3.interfaces.dto.S3UrlDtoResponse

interface S3UrlGenerationService {

    fun getPresignedUrl(fileName: String): S3UrlDtoResponse
}
