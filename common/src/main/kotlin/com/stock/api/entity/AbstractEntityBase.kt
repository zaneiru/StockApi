package com.stock.api.entity

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.util.ProxyUtils
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.Version

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class AbstractEntityBase<PK : Serializable>: Serializable {

    // Abstract class를 상속받는 class에서 proeprty를 사용하기 위해서는
    // Abstract class 내 property 에 open 키워드를 써줘야 한다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: PK? = null

    @Version
    @Column(nullable = false)
    var version: Long? = null

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var createdDate: LocalDateTime = LocalDateTime.MIN
        private set

    @Column(name = "last_modified_date")
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var lastModifiedDate: LocalDateTime = LocalDateTime.MIN
        private set


    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as AbstractEntityBase<*>

        return if (null == this.id) false else this.id == other.id
    }

    override fun hashCode(): Int {
        return 31
    }

    override fun toString() = "Entity of type ${this.javaClass.name} with id: $id"

    companion object {
        private const val serialVersionUID = 12398467923L
    }
}