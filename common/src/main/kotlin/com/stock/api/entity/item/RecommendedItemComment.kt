package com.stock.api.entity.item

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.fasterxml.jackson.annotation.JsonBackReference
import com.stock.api.entity.AbstractEntityBase
import com.stock.api.entity.NoArg
import com.stock.api.entity.member.Member
import com.stock.api.model.enums.AdminConfig
import com.stock.api.model.enums.UserConfig
import org.apache.commons.lang3.builder.ToStringExclude
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@NoArg
@Entity
@DynamicInsert
@DynamicUpdate
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class RecommendedItemComment(

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "recommended_item_id")
    @JsonBackReference
    @ToStringExclude
    var recommendedItem: RecommendedItem? = null,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_uuid", nullable = false, referencedColumnName = "uuid")
    @JsonBackReference
    @ToStringExclude
    var member: Member? = null,

    @Column(nullable = false)
    var ip: String?,

    @Column(nullable = false)
    var comment: String?,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var userConfig: UserConfig,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var adminConfig: AdminConfig

): AbstractEntityBase<Long>() {
    companion object {
        private const val serialVersionUID = 39846722893L
        private val equalsAndHashCodeProperties = arrayOf(RecommendedItemComment::id)
        private val toStringProperties = arrayOf(
            RecommendedItemComment::recommendedItem,
            RecommendedItemComment::adminConfig,
            RecommendedItemComment::comment,
            RecommendedItemComment::ip,
            RecommendedItemComment::member,
            RecommendedItemComment::userConfig,
            RecommendedItemComment::id,
            RecommendedItemComment::version,
            RecommendedItemComment::createdDate,
            RecommendedItemComment::lastModifiedDate
        )
    }

    override fun toString() = kotlinToString(properties = toStringProperties)
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)
}

