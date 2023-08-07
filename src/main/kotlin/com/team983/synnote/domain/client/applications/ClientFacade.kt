package com.team983.synnote.domain.client.applications

import com.team983.synnote.domain.client.domain.service.ClientService
import com.team983.synnote.domain.client.interfaces.dto.PresignedUrlResponse
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class ClientFacade(
    private val clientService: ClientService
) {
    fun getPresignedUrl(fileName: String): PresignedUrlResponse = clientService.getPresignedUrl(fileName)
}
