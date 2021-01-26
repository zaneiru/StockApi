package com.stock.api.service.notice

import com.stock.api.model.enums.AdminConfig
import com.stock.api.entity.notice.NoticeBoard
import com.stock.api.repository.noticeBoards.NoticeBoardRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class NoticeBoardService(private val noticeBoardRepository: NoticeBoardRepository) {

    @Transactional(readOnly = true)
    fun getNoticeBoards(pageable: Pageable): Page<NoticeBoard> {
        val result = noticeBoardRepository.findByAdminConfig(AdminConfig.ON, pageable)
        return result
    }



//    @Transactional(readOnly = true)
//    fun getNoticeBoards(pageable: Pageable): Page<NoticeBoard> =
//        noticeBoardRepository.findByAdminConfig(AdminConfig.ON, pageable)

    @Transactional(readOnly = true)
    fun getNoticeBoard(id: Long): Mono<NoticeBoard> =
        Mono.fromCallable { noticeBoardRepository.findById(id).orElseThrow { Exception("") } }
            .subscribeOn(Schedulers.parallel())
}