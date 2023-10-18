package com.team983.synnote.domain.note.domains.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "memo")
class Memo(
    text: String,
    start: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column(name = "text", nullable = false)
    var text: String = text
        protected set

    @Column(name = "start", nullable = true)
    var start: Long = start
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", nullable = false)
    var note: Note? = null
        protected set

    fun linkToNote(note: Note) {
        this.note = note
    }
}
