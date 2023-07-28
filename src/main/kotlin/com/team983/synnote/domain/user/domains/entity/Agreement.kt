package com.team983.synnote.domain.user.domains.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "agreement")
class Agreement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne(mappedBy = "agreement")
    var user: User? = null,

    @Column(name = "privacy_policy", nullable = false, columnDefinition = "TINYINT")
    var privacyPolicy: Boolean = false,

    @Column(name = "terms_and_cons", nullable = false, columnDefinition = "TINYINT")
    var termsAndCons: Boolean = false,

    @Column(name = "service_improvement", nullable = false, columnDefinition = "TINYINT")
    var serviceImprovement: Boolean = false,

    @Column(name = "privacy_policy_date", nullable = false)
    var privacyPolicyDate: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "terms_and_cons_date", nullable = false)
    var termsAndConsDate: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "service_improvement_date", nullable = false)
    var serviceImprovementDate: LocalDateTime? = LocalDateTime.now()
)
