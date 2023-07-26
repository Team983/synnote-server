package com.team983.synnote.sample

import com.team983.synnote.common.authority.decodeJwt
import com.team983.synnote.common.dto.UserAttributeDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/samples")
@RestController
class SampleController {

    @GetMapping("{sampleId}")
    fun getSampleById(
        @PathVariable sampleId: String
    ): SampleResponse =
        SampleResponse(sampleId, "sample-$sampleId")

    @GetMapping("user-info")
    fun getUserInfo(
        @RequestHeader("x-amzn-oidc-data") encodedJwt: String
    ): UserAttributeDto {
        return decodeJwt(encodedJwt)
    }
}

data class SampleResponse(
    val sampleId: String,
    val name: String
)
