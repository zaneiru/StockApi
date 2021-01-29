package com.stock.api.controller.member.to

import com.stock.api.model.enums.AuthVendorType
import com.stock.api.model.enums.MemberStatus
import com.stock.api.entity.member.Member
import com.stock.api.entity.product.MembershipProduct
import java.time.LocalDateTime

data class MemberResponseTO (
    var uuid: String,
    var status: MemberStatus,
    var authVendorType: AuthVendorType,
    var accountId: String,
    var email: String,
    var membershipProduct: MembershipProduct?,
    var membershipStartDate: LocalDateTime?,
    var membershipEndDate: LocalDateTime?
)

fun Member.toMemberResponseTO(): MemberResponseTO {
    return MemberResponseTO(
        uuid = this.uuid!!,
        status = this.status!!,
        authVendorType = this.authVendorType!!,
        accountId = this.accountId!!,
        email = this.email!!,
        membershipProduct = this.membershipProduct,
        membershipStartDate = this.membershipStartDate,
        membershipEndDate = this.membershipEndDate
    )
}

