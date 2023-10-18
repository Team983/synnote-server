package com.team983.synnote.common.utils

import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Component
class RestTemplateRequester {

    fun <T> sendRequestToWhisper(
        url: String,
        responseType: Class<T>
    ): T {
        val restTemplate = RestTemplate()

        val uri: URI = UriComponentsBuilder.fromHttpUrl(url)
            .build()
            .toUri()

        val requestEntity: RequestEntity<Any> = RequestEntity<Any>(
            HttpMethod.GET,
            uri
        )

        try {
            val responseEntity: ResponseEntity<T> = restTemplate.exchange(requestEntity, responseType)
            if (!responseEntity.statusCode.is2xxSuccessful) {
                throw RuntimeException()
            }
            return responseEntity.body ?: throw RuntimeException()
        } catch (ex: Exception) {
            throw RuntimeException()
        }
    }

    fun <T> sendRequestToWhisperX(
        noteId: Long,
        filename: String,
        domainType: String,
        responseType: Class<T>
    ): T {
        val restTemplate = RestTemplate()

        val url = "https://team983.site/asr/$noteId"
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        val body = AsrRequest(filename, domainType)

        log.info("전사 요청 => $filename")

        val uri: URI = UriComponentsBuilder.fromHttpUrl(url)
            .build()
            .toUri()

        val requestEntity: RequestEntity<Any> = RequestEntity<Any>(
            body,
            headers,
            HttpMethod.POST,
            uri
        )

        try {
            val responseEntity: ResponseEntity<T> = restTemplate.exchange(requestEntity, responseType)
            if (!responseEntity.statusCode.is2xxSuccessful) {
                throw RuntimeException()
            }
            return responseEntity.body ?: throw RuntimeException()
        } catch (ex: Exception) {
            throw RuntimeException("전사 실패 => $noteId, $filename")
        }
    }
}

data class AsrRequest(
    val filename: String,
    val category: String
)
