package com.synnote.record

import com.synnote.record.common.config.AmazonS3Config
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AmazonS3Config::class)
class RecordApplication

fun main(args: Array<String>) {
    runApplication<RecordApplication>(*args)
}
