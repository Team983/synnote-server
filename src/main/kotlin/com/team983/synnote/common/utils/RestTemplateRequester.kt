package com.team983.synnote.common.utils

import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Component
class RestTemplateRequester {

    fun <T> sendRequest(
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
}
