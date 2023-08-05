package com.team983.synnote.domain.note.domains.entity

import com.team983.synnote.domain.note.domains.enums.AsrType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "script")
class Script(
    asrType: AsrType,
    text: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "asr_type", nullable = false, length = 15)
    val asrType: AsrType = asrType

    @Column(name = "text", nullable = false)
    var text: String = text
        protected set

    @Column(name = "speaker", nullable = true, length = 20)
    var speaker: String? = null
        protected set

    @Column(name = "start", nullable = true)
    val start: Long? = null

    @Column(name = "end", nullable = true)
    val end: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", nullable = false)
    var note: Note? = null
        protected set

    fun linkToNote(note: Note) {
        this.note = note
    }
}