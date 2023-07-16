package com.synnote.record.domain.s3.domain

interface S3UrlGenerationService {

    fun getPresignedUrl(fileName: String): Map<String, Any>?
}
