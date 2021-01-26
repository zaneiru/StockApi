package com.stock.api.controller.product.to

import com.stock.api.entity.product.MembershipProduct

data class MembershipProductResponseTO (
    var code: String,
    var name: String,
    var amount: Int,
    var discountedAmount: Int,
    var allowablePeriod: Int,
    var freeConfig: String
)

fun MembershipProduct.toMembershipProductResponseTO(): MembershipProductResponseTO {
    return MembershipProductResponseTO(
        code = this.code.toString(),
        name = this.name,
        amount = this.amount,
        discountedAmount = this.discountedAmount,
        allowablePeriod = this.allowablePeriod,
        freeConfig = this.freeConfig.toString()
    )
}