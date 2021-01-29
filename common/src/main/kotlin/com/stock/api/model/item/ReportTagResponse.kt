package com.stock.api.model.item

import com.stock.api.entity.item.ReportTag

data class ReportTagResponse (
    val tags: TagResponse
)

fun ReportTag.toReportTagResponse(): ReportTagResponse {
    return ReportTagResponse(
        tags = this.tag!!.toTagResponse()
    )
}
