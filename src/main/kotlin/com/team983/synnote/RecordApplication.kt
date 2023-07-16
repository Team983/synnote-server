package com.team983.synnote

import com.team983.synnote.common.config.AmazonS3Config
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AmazonS3Config::class)
class SynnoteApplication

fun main(args: Array<String>) {
    runApplication<SynnoteApplication>(*args)
}
