package com.team983.synnote.domain.client.domain.service

import com.team983.synnote.domain.client.interfaces.dto.PresignedUrlResponse

interface ClientService {

    fun getPresignedUrl(): PresignedUrlResponse
}
