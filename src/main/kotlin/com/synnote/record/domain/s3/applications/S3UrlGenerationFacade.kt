package com.synnote.record.domain.s3.applications

import com.synnote.record.domain.s3.domain.S3UrlGenerationService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class S3UrlGenerationFacade(
    private val s3UrlGenerationService: S3UrlGenerationService
) {
    fun getPresignedUrl(fileName: String): Map<String, Any>? = s3UrlGenerationService.getPresignedUrl(fileName)
}
