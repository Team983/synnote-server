package com.team983.synnote.domain.note.interfaces.dto

// 요약 결과에 대한 응답
data class SummaryResultResponse(
    val userId: String,
    val noteId: Long,
    val summary: String,
    val summaryStatus: String,
    val keywords: Keywords,
    val keywordStatus: String
) {
    data class Keywords(
        val nodes: List<Node>,
        val relations: List<Relation>
    )

    data class Node(
        val id: Long,
        val label: String,
        val type: String
    )

    data class Relation(
        val source: Long,
        val target: Long,
        val label: String
    )
}
