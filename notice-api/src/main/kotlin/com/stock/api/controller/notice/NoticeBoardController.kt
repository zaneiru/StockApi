package com.stock.api.controller.notice

import com.stock.api.controller.notice.NoticeBoardController.Companion.BASE_URI
import com.stock.api.controller.notice.to.NoticeBoardResponseTO
import com.stock.api.controller.notice.to.toNoticeBoardResponseTO
import com.stock.api.entity.notice.NoticeBoard
import com.stock.api.service.notice.NoticeBoardService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@Api(description = "공지사항 API")
@RestController
@RequestMapping(value = [BASE_URI])
class NoticeBoardController(val noticeBoardService: NoticeBoardService) {

    @ApiOperation("공지사항 목록 조회")
    @GetMapping
    fun getNoticeBoards(pageable: Pageable): Mono<Page<NoticeBoardResponseTO>> {
        val noticeBoards = noticeBoardService.getNoticeBoards(pageable)
        return Mono.just(noticeBoards.map(NoticeBoard::toNoticeBoardResponseTO))
    }

    @ApiOperation("공지사항 조회")
    @GetMapping("/{id}")
    fun getNoticeBoard(@PathVariable id: Long): Mono<NoticeBoardResponseTO> {
        val noticeBoard = noticeBoardService.getNoticeBoard(id)
        return noticeBoard.map(NoticeBoard::toNoticeBoardResponseTO)
    }

    companion object {
        const val BASE_URI: String = "/api/v1/noticeBoards"
    }
}