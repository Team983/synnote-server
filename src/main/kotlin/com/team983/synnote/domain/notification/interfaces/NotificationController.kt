package com.team983.synnote.domain.notification.interfaces

import com.team983.synnote.domain.notification.services.NotificationService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RequestMapping("/api/v1/notification")
@RestController
class NotificationController(
    private val notificationService: NotificationService
) {

//    @PostMapping("/send-data/{id}")
//    fun sendData(@PathVariable id: Long) {
//        val iid: String = id.toString()
//        this.eventRedisOperations.convertAndSend(getChannelName(iid), SummaryCompletedDto(iid, 1))
//    }
//
//    private fun getChannelName(memberId: String): String {
//        return "sample:topics:$memberId"
//    }

    @GetMapping(value = ["/subscribe/{userId}"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun subscribe(@PathVariable userId: String): SseEmitter? {
        return notificationService.subscribe(userId)
    }
}
