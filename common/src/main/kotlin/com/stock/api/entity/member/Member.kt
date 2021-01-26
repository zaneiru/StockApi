package com.stock.api.entity.member

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.stock.api.model.enums.AuthVendorType
import com.stock.api.model.enums.DeviceType
import com.stock.api.model.enums.DeviceTypeConverter
import com.stock.api.model.enums.MemberStatus
import com.stock.api.entity.AbstractEntityBase
import com.stock.api.entity.NoArg
import com.stock.api.entity.item.RecommendedItemComment
import com.stock.api.entity.item.RecommendedItemLike
import com.stock.api.entity.product.MembershipProduct
import org.apache.commons.lang3.builder.ToStringExclude
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.NaturalId
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@DynamicInsert
@DynamicUpdate
@Configurable
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class Member(

    @NaturalId
    @Column(nullable = false, name = "uuid")
    var uuid: String? = null,

    @Column(nullable = false)
    val nickName: String? = null,

    @Column(nullable = false)
    val profileImageUrl: String? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: MemberStatus? = null,

    @Column(nullable = true)
    var membershipStartDate: LocalDateTime? = null,

    @Column(nullable = true)
    var membershipEndDate: LocalDateTime? = null,

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    val authVendorType: AuthVendorType? = null,

    @Column(nullable = true)
    val accountId: String? = null,

    @Column(nullable = true)
    @Email
    val email: String? = null,

    @Column(nullable = true)
    val gender: String? = null,

    @Column(nullable = true)
    val age: Int? = null,

    @Column(nullable = false)
    val kakaoNumber: String? = null,

    @Column(nullable = false)
    var kakaoNickname: String? = null,

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var lastAppAccessDate: LocalDateTime? = null,

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var lastLoginDate: LocalDateTime? = null,

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val lastSignupDate: LocalDateTime? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Convert(converter = DeviceTypeConverter::class)
    val device: DeviceType? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_product_code", insertable = false, updatable = false)
    @ToStringExclude
    val membershipProduct: MembershipProduct? = null,

    @JsonManagedReference
    @ToStringExclude
    @OneToMany(mappedBy="member", fetch = FetchType.LAZY, orphanRemoval = true)
    val recommendedItemComments: Set<RecommendedItemComment>? = mutableSetOf(),

    @JsonManagedReference
    @ToStringExclude
    @OneToMany(mappedBy="member", fetch = FetchType.LAZY, orphanRemoval = true)
    val recommendedItemLikes: Set<RecommendedItemLike>? = mutableSetOf(),

) : AbstractEntityBase<Long>() {
    companion object {
        private const val serialVersionUID = 12147552L;
        private val equalsAndHashCodeProperties = arrayOf(Member::id)
        private val toStringProperties = arrayOf(
            Member::accountId,
            Member::email,
            Member::gender,
            Member::kakaoNickname,
            Member::kakaoNumber,
            Member::nickName,
            Member::profileImageUrl,
            Member::uuid,
            Member::age,
            Member::authVendorType,
            Member::device,
            Member::membershipProduct,
            Member::lastAppAccessDate,
            Member::lastLoginDate,
            Member::lastSignupDate,
            Member::membershipEndDate,
            Member::membershipStartDate,
            Member::status,
            Member::id,
            Member::version,
            Member::createdDate,
            Member::lastModifiedDate,
            Member::recommendedItemComments,
            Member::recommendedItemLikes
        )
    }

    override fun toString() = kotlinToString(properties = toStringProperties)
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)
}


