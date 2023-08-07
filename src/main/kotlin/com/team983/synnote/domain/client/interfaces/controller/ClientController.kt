package com.team983.synnote.domain.client.interfaces.controller

import com.team983.synnote.common.dto.BaseResponse
import com.team983.synnote.domain.client.applications.ClientFacade
import com.team983.synnote.domain.client.interfaces.dto.PresignedUrlResponse
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("api/v1/client")
@RestController
class ClientController(
    private val clientFacade: ClientFacade
) {

    @GetMapping("presigned-url/{fileName}")
    @ResponseStatus(OK)
    fun getPresignedUrl(
        @PathVariable fileName: String
    ): BaseResponse<PresignedUrlResponse> = BaseResponse(data = clientFacade.getPresignedUrl(fileName))
}
