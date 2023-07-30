package com.team983.synnote.domain.note.domains.entity

import com.team983.synnote.domain.note.domains.enums.DomainType
import com.team983.synnote.domain.note.domains.enums.Status
import com.team983.synnote.domain.note.domains.enums.UploadType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "note")
class Note(
    domainType: DomainType,
    status: Status,
    uploadType: UploadType,
    userId: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column(name = "title", nullable = false, length = 50)
    var title: String = "새로운 노트"
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "domain", nullable = false, length = 30)
    val domainType: DomainType = domainType

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    var status: Status = status
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "upload_type", nullable = false, length = 10)
    val uploadType: UploadType = uploadType

    @Column(name = "deleted_flag", nullable = false, columnDefinition = "TINYINT")
    var deletedFlag: Boolean = false
        protected set

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    var createdDate: LocalDateTime? = null
        protected set

    @LastModifiedDate
    @Column(name = "updated_date", nullable = false)
    var updatedDate: LocalDateTime? = null
        protected set

    @Column(name = "user_id", nullable = false)
    val userId: String = userId
}
