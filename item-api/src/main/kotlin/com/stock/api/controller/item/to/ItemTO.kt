package com.stock.api.controller.item.to

import org.hibernate.cache.spi.support.AbstractReadWriteAccess

data class ItemTO (
    var id: Long,
    var code: String,
    var name: String,
    var amount: Int,
    var discountedAmount: Int,
    var discountPercent: Double,
    var allowablePeriod: Int
)
//
//fun AbstractReadWriteAccess.Item.toItemTO(): ItemTO {
//    return ItemTO(
//        id = this.id,
//        code = this.code,
//        name = this.name,
//        amount = this.amount,
//        discountedAmount = this.discountedAmount,
//        discountPercent = this.discountPercent,
//        allowablePeriod = this.allowablePeriod
//    )
//}