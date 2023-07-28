package com.team983.synnote.domain.user.domains.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "users")
class User(
    @Id
    @Column(name = "id", nullable = false, length = 36)
    val id: String,

    @Column(name = "name", nullable = false, length = 320)
    val name: String,

    @Column(name = "email", nullable = false, length = 320)
    val email: String,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "agreement_id")
    val agreement: Agreement,

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    var createdDate: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "updated_date", nullable = false)
    var updatedDate: LocalDateTime? = null,

    @Column(name = "removed_date", nullable = false)
    var removedDate: LocalDateTime? = LocalDateTime.of(2000, 1, 1, 1, 1)
)
