package com.stock.api.entity.item

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.stock.api.entity.AbstractEntityBase
import com.stock.api.model.enums.AdminConfig
import org.apache.commons.lang3.builder.ToStringExclude
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.beans.factory.annotation.Configurable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.OneToMany

@Entity
@DynamicInsert
@DynamicUpdate
@Configurable
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class RecommendedItem(

    @Column(nullable = false)
    val title: String?,

    @Column(nullable = false)
    val bannerUrl: String?,

    @Column(nullable = false)
    val bannerMainTitle: String?,

    @Column(nullable = false)
    val bannerSubTitle: String?,

    @Column(nullable = false)
    val likeCount: Int = 0,

    @Column(nullable = false)
    val viewCount: Int = 0,

    @Column(nullable = false)
    var commentCount: Int = 0,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val adminConfig: AdminConfig,

    @ToStringExclude
    @OneToMany(mappedBy = "recommendedItem", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    val recommendedItemComments: List<RecommendedItemComment>? = mutableListOf(),

    @ToStringExclude
    @OneToMany(mappedBy = "recommendedItem", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    val recommendedItemLikes: Set<RecommendedItemLike>? = mutableSetOf()

): AbstractEntityBase<Long>() {
    companion object {
        private const val serialVersionUID = 2311234467923L
        private val equalsAndHashCodeProperties = arrayOf(RecommendedItem::id)
        private val toStringProperties = arrayOf(
            RecommendedItem::title,
            RecommendedItem::bannerUrl,
            RecommendedItem::bannerMainTitle,
            RecommendedItem::bannerSubTitle,
            RecommendedItem::likeCount,
            RecommendedItem::viewCount,
            RecommendedItem::commentCount,
            RecommendedItem::adminConfig,
            RecommendedItem::recommendedItemComments,
            RecommendedItem::recommendedItemLikes,
            RecommendedItem::id,
            RecommendedItem::version,
            RecommendedItem::createdDate,
            RecommendedItem::lastModifiedDate
        )
    }

    override fun toString() = kotlinToString(properties = toStringProperties)
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)
}
