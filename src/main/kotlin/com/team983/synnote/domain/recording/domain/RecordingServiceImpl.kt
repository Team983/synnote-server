package com.team983.synnote.domain.recording.domain

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.team983.synnote.common.config.AmazonS3Config
import com.team983.synnote.domain.recording.interfaces.dto.PresignedUrlResponse
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Date

@Service
class RecordingServiceImpl(
    private val amazonS3Config: AmazonS3Config
) : RecordingService {
    override fun getPresignedUrl(fileName: String): PresignedUrlResponse {
        val encodedFileName = "${fileName}_${LocalDateTime.now()}"
        val objectKey = "full/$encodedFileName"

        val expiration = Date()
        var expTimeMillis: Long = expiration.time
        expTimeMillis += (3 * 60 * 1000).toLong() // 3분
        expiration.time = expTimeMillis // url 만료 시간 설정

        val generatePresignedUrlRequest: GeneratePresignedUrlRequest =
            GeneratePresignedUrlRequest(amazonS3Config.bucketName, objectKey)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration)

        return PresignedUrlResponse(
            amazonS3Config.amazonS3().generatePresignedUrl(generatePresignedUrlRequest),
            encodedFileName
        )
    }
}
