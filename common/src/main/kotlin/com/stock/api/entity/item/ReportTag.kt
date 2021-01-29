package com.stock.api.entity.item

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.fasterxml.jackson.annotation.JsonBackReference
import com.stock.api.entity.AbstractEntityBase
import com.stock.api.entity.common.Tag
import org.apache.commons.lang3.builder.ToStringExclude
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
class ReportTag (

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    @JsonBackReference
    @ToStringExclude
    var report: Report? = null,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    @JsonBackReference
    @ToStringExclude
    var tag: Tag? = null,

    ): AbstractEntityBase<Long>() {
    companion object {
        private const val serialVersionUID = -90812234467923L
        private val equalsAndHashCodeProperties = arrayOf(ReportTag::id)
        private val toStringProperties = arrayOf(
            ReportTag::id,
            ReportTag::version,
            ReportTag::createdDate,
            ReportTag::lastModifiedDate
        )
    }

    override fun toString() = kotlinToString(properties = toStringProperties)
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)
}
