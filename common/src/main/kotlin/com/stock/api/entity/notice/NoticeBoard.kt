package com.stock.api.entity.notice

import com.stock.api.model.enums.AdminConfig
import com.stock.api.model.enums.NoticeType
import com.stock.api.entity.AbstractEntityBase
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@DynamicInsert
@DynamicUpdate
data class NoticeBoard(

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        val noticeType: NoticeType,

        @Column(nullable = false)
        @Type(type = "yes_no")
        val topPosition: Boolean,

        @Column(nullable = false)
        val title: String,

        @Column(nullable = false)
        val content: String,

        @Column(nullable = false)
        val viewCount: Int,

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        val adminConfig: AdminConfig
): AbstractEntityBase<Long>() {
        companion object {
                private const val serialVersionUID = 2398467923L
        }
}

