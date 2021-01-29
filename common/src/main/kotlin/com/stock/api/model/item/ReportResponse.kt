package com.stock.api.model.item

import com.stock.api.entity.item.Report
import com.stock.api.entity.item.ReportTag
import com.stock.api.model.enums.ReportType

data class ReportResponse (
    val id: Long,
    val type: ReportType,
    val title: String,
    val contents: String,
    val reportDate: String,
    val viewCount: Int,
    val fileUrl: String,
    val reportTags: List<ReportTagResponse>
)

fun Report.toReportResponse(): ReportResponse {
    return ReportResponse(
        id = this.id!!,
        type = this.type!!,
        title = this.title!!,
        contents = this.contents!!,
        reportDate = this.reportDate!!,
        viewCount = this.viewCount,
        fileUrl = this.fileUrl!!,
        reportTags = this.reportTags!!.map(ReportTag::toReportTagResponse)
    )
}