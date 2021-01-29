package com.stock.api.entity.item

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.stock.api.entity.AbstractEntityBase
import com.stock.api.model.enums.AdminConfig
import com.stock.api.model.enums.ReportType
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
import javax.persistence.OrderBy
import au.com.console.jpaspecificationdsl.and
import au.com.console.jpaspecificationdsl.equal
import org.springframework.data.jpa.domain.Specification

@Entity
@DynamicInsert
@DynamicUpdate
@Configurable
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class Report (

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val type: ReportType? = null,

    @Column(nullable = false)
    val title: String? = null,

    @Column(nullable = false)
    val contents: String? = null,

    @Column(nullable = false)
    val reportDate: String? = null,

    @Column(nullable = false)
    val fileUrl: String? = null,

    @Column(nullable = false)
    var viewCount: Int = 0,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val adminConfig: AdminConfig = AdminConfig.OFF,

    @OrderBy("last_modified_date DESC")
    @OneToMany(mappedBy = "report", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    val reportTags: List<ReportTag>? = mutableListOf(),

): AbstractEntityBase<Long>() {
    companion object {
        private const val serialVersionUID = -911092234467923L
        private val equalsAndHashCodeProperties = arrayOf(Report::id)
        private val toStringProperties = arrayOf(
            Report::type,
            Report::title,
            Report::contents,
            Report::reportDate,
            Report::fileUrl,
            Report::viewCount,
            Report::adminConfig,
            Report::reportTags,
            Report::id,
            Report::version,
            Report::createdDate,
            Report::lastModifiedDate
        )
    }

    override fun toString() = kotlinToString(properties = toStringProperties)
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)
}

