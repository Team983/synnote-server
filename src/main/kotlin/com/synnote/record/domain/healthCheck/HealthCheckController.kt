package com.synnote.record.domain.healthCheck

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping("api/healthy")
    fun getHealthCheck(): String {
        return "healthy"
    }
}
