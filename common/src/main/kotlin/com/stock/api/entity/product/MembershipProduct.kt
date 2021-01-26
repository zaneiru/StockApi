package com.stock.api.entity.product

import com.stock.api.model.enums.AdminConfig
import com.stock.api.model.enums.MembershipProductCode
import com.stock.api.model.enums.YesNoConfig
import com.stock.api.entity.AbstractEntityBase
import com.stock.api.entity.member.Member
import org.apache.commons.lang3.builder.ToStringExclude
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class MembershipProduct (

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val code: MembershipProductCode,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val amount: Int,

    @Column(nullable = false)
    val discountedAmount: Int,

    @Column(nullable = false)
    val allowablePeriod: Int,

    @Column(nullable = false)
    val discountPercent: Double,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val freeConfig: YesNoConfig,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val showConfig: YesNoConfig,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val adminConfig: AdminConfig,

    @ToStringExclude
    @OneToMany(mappedBy="membershipProduct", fetch = FetchType.LAZY, orphanRemoval = true)
    val members: List<Member>

) : AbstractEntityBase<Long>() {
    companion object {
        private const val serialVersionUID = 8880748357388315402L
    }
}

