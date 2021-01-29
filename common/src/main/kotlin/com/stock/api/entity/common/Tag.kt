package com.stock.api.entity.common

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.stock.api.entity.AbstractEntityBase
import com.stock.api.entity.item.ReportTag
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.beans.factory.annotation.Configurable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.OneToMany

@Entity
@DynamicInsert
@DynamicUpdate
@Configurable
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class Tag (

    @Column(nullable = false)
    val name: String,

    @JsonManagedReference
    @OneToMany(mappedBy="tag", fetch = FetchType.LAZY, orphanRemoval = true)
    val reportTags: Set<ReportTag>? = mutableSetOf(),

): AbstractEntityBase<Long>() {
    companion object {
        private const val serialVersionUID = 2311234467923L
        private val equalsAndHashCodeProperties = arrayOf(Tag::id)
        private val toStringProperties = arrayOf(
            Tag::name,
            Tag::reportTags,
            Tag::id,
            Tag::version,
            Tag::createdDate,
            Tag::lastModifiedDate
        )
    }

    override fun toString() = kotlinToString(properties = toStringProperties)
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)
}