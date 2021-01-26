package com.stock.api.repository.noticeBoards

import com.stock.api.model.enums.AdminConfig
import com.stock.api.entity.notice.NoticeBoard
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NoticeBoardRepository : JpaRepository<NoticeBoard, Long> {

    fun findByAdminConfig(adminConfig: AdminConfig, pageable: Pageable) : Page<NoticeBoard>

    override fun findById(id: Long) : Optional<NoticeBoard>

}