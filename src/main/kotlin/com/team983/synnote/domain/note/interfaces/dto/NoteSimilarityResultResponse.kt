package com.team983.synnote.domain.note.interfaces.dto

// 전체 노트 시각화
data class NoteSimilarityResultResponse(
    val userId: String,
    val noteIds: List<Long>,
    val similarities: List<Similarity>,
    val status: String
) {
    data class Similarity(
        val source: Long,
        val target: Long,
        val value: Long
    )
}
