package com.stock.api.controller.notice.to

import com.stock.api.entity.notice.NoticeBoard
import org.springframework.data.domain.Page
import java.time.LocalDateTime

data class NoticeBoardResponseTO (
    var id: Long,
    var noticeType: String,
    var topPosition: Boolean,
    var title: String,
    var content: String,
    var viewCount: Int,
    var lastModifiedDate: LocalDateTime
)

fun NoticeBoard.toNoticeBoardResponseTO(): NoticeBoardResponseTO {
    return NoticeBoardResponseTO(
        id = this.id!!,
        noticeType = this.noticeType.toString(),
        topPosition = this.topPosition,
        title = this.title,
        content = this.content,
        viewCount = this.viewCount,
        lastModifiedDate = this.lastModifiedDate
    )
}



