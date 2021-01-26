package com.stock.api.controller.to

import com.stock.api.entity.news.News
import java.time.LocalDateTime


data class NewsResponseTO (
    val id: Long?,
    var type: String,
    var press: String?,
    var title: String?,
    var link: String?,
    var scrapeDate: LocalDateTime,
    var fdaRequester: String?,
    var fdaProductName: String?,
    var fdaStep: Int?,
    var fdaInstitution: String?,
    var fdaApprovalType: String?,
    var fdaApprovalDate: LocalDateTime?,
    var fdaClinicalContent: String?
)

fun News.toNewsResponseTO(): NewsResponseTO {
    return NewsResponseTO(
        id = this.id,
        type = this.type.toString(),
        press = this.press,
        title = this.title,
        link = this.link,
        scrapeDate = this.scrapeDate,
        fdaRequester = this.fdaRequester,
        fdaProductName = this.fdaProductName,
        fdaStep = this.fdaStep,
        fdaInstitution = this.fdaInstitution,
        fdaApprovalType = this.fdaApprovalType.toString(),
        fdaApprovalDate = this.fdaApprovalDate,
        fdaClinicalContent = this.fdaClinicalContent
    )
}