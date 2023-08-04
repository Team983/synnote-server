package com.team983.synnote.domain.note.domains.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "recording")
class Recording(
    s3ObjectUrl: String,
    recordingDuration: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column(name = "s3_object_url", nullable = false)
    val s3ObjectUrl: String = s3ObjectUrl

    @Column(name = "recording_duration", nullable = false)
    val recordingDuration: Long = recordingDuration
}
