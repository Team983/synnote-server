package com.team983.synnote.domain.s3.interfaces.controller

import com.team983.synnote.common.dto.BaseResponse
import com.team983.synnote.domain.s3.applications.S3UrlGenerationFacade
import com.team983.synnote.domain.s3.interfaces.dto.S3UrlDtoResponse
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/s3")
@RestController
class S3UrlGenerationController(
    private val facade: S3UrlGenerationFacade
) {

    @GetMapping("presigned-url/{fileName}")
    @ResponseStatus(OK)
    fun getPresignedUrl(
        @PathVariable fileName: String
    ): BaseResponse<S3UrlDtoResponse> = BaseResponse(data = facade.getPresignedUrl(fileName))
}
