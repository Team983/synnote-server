package com.team983.synnote.domain.s3.applications

import com.team983.synnote.domain.s3.domain.S3UrlGenerationService
import com.team983.synnote.domain.s3.interfaces.dto.S3UrlDtoResponse
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class S3UrlGenerationFacade(
    private val s3UrlGenerationService: S3UrlGenerationService
) {
    fun getPresignedUrl(fileName: String): S3UrlDtoResponse = s3UrlGenerationService.getPresignedUrl(fileName)
}
