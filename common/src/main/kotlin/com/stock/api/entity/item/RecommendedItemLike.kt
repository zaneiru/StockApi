package com.stock.api.entity.item

import com.stock.api.entity.AbstractEntityBase
import com.stock.api.entity.member.Member
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.beans.factory.annotation.Configurable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@DynamicInsert
@DynamicUpdate
@Configurable
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class RecommendedItemLike (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommended_item_id")
    val recommendedItem: RecommendedItem,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_uuid")
    val member: Member,

    @Column(nullable = false)
    val ip: String

): AbstractEntityBase<Long>() {
    companion object {
        private const val serialVersionUID = 2398467923L
    }

    override fun toString(): String {
        return super.toString()
    }
}
