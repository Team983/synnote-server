package com.team983.synnote.domain.healthCheck

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping("/healthy")
    fun getHealthCheck(): String {
        return "healthy"
    }
}
