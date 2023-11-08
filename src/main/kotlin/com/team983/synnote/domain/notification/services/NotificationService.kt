package com.team983.synnote.domain.notification.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.team983.synnote.domain.notification.dtos.CompletedDto
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.concurrent.CopyOnWriteArrayList

@Service
class NotificationService(
    private val eventRedisOperations: RedisOperations<String, CompletedDto>,
    private val redisMessageListenerContainer: RedisMessageListenerContainer,
    private val objectMapper: ObjectMapper
) {
    companion object {
        private const val DEFAULT_TIMEOUT = 60L * 1000
        private val emitters = CopyOnWriteArrayList<SseEmitter>()
    }

    fun sendSummaryCompleted(userId: String, noteId: Long) {
        this.eventRedisOperations.convertAndSend(
            getChannelName(userId), CompletedDto("Summmary", userId, noteId, "")
        )
    }

    fun sendQueryCompleted(userId: String, noteId: Long, text: String) {
        this.eventRedisOperations.convertAndSend(
            getChannelName(userId),
            CompletedDto("Query", userId, noteId, text)
        )
    }

    fun subscribe(userId: String): SseEmitter? {
        val emitter = SseEmitter(DEFAULT_TIMEOUT)

        //초반 연결용 메시지
        emitter.send(
            SseEmitter.event()
                .id(userId)
                .name("sse")
        )

        emitters.add(emitter)

        val messageListener = MessageListener { message, pattern ->
            val serialize: CompletedDto = serialize(message)
            val type = serialize.type
            sendToClient(emitter, userId, serialize, type)
        }

        redisMessageListenerContainer.addMessageListener(messageListener, ChannelTopic.of(getChannelName(userId)))
        checkEmitterStatus(emitter, messageListener)
        return emitter
    }

    private fun sendToClient(emitter: SseEmitter, id: String, data: Any, type: String) {
        try {
            emitter.send(
                SseEmitter.event()
                    .id(id)
                    .name(type)
                    .data(data)
            )
        } catch (e: IOException) {
            emitters.remove(emitter)
        }
    }

    private fun getChannelName(userId: String): String {
        return "sample:topics:$userId"
    }

    private fun serialize(message: Message): CompletedDto {
        return objectMapper.readValue(message.getBody(), CompletedDto::class.java)
    }

    private fun checkEmitterStatus(emitter: SseEmitter, messageListener: MessageListener) {
        emitter.onCompletion {
            emitters.remove(emitter)
            redisMessageListenerContainer.removeMessageListener(messageListener)
        }
        emitter.onTimeout {
            emitters.remove(emitter)
            redisMessageListenerContainer.removeMessageListener(messageListener)
        }
    }
}
