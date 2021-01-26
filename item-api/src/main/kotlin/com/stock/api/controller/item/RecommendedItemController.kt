package com.stock.api.controller.item

import com.stock.api.controller.item.RecommendedItemController.Companion.BASE_URI
import com.stock.api.entity.item.RecommendedItem
import com.stock.api.model.item.RecommendedItemCommentRequest
import com.stock.api.model.item.RecommendedItemCommentResponse
import com.stock.api.model.item.RecommendedItemResponse
import com.stock.api.model.item.toRecommendedItemCommentResponse
import com.stock.api.model.item.toRecommendedItemResponse
import com.stock.api.service.item.RecommendedItemService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@Api(description = "추천종목 API")
@RestController
@RequestMapping(value = [BASE_URI])
class RecommendedItemController(val service: RecommendedItemService) {

    companion object {
        const val BASE_URI: String = "/api/v1/recommendedItems"
    }

    @ApiOperation("테마추천 목록 조회")
    @GetMapping
    fun getRecommendedItems(pageable: Pageable): Mono<Page<RecommendedItemResponse>> {
        val items = service.getRecommendedItems(pageable)
        return Mono.just(items.map(RecommendedItem::toRecommendedItemResponse))
    }

    @ApiOperation("테마추천 조회")
    @GetMapping("/{id}")
    fun getRecommendedItem(@PathVariable id: Long): Mono<RecommendedItemResponse>? {
        val item = service.getRecommendedItem(id)
        return item.recommendedItemComments?.let { item.toRecommendedItemResponse(it) }?.let { Mono.just(it) }
    }

    @ApiOperation("테마추천 코멘트 목록 조회")
    @GetMapping("/comments")
    fun getRecommendedItemComments(@RequestHeader headers: Map<String, String>,
                                   @RequestParam recommendedItemId: Long) {

    }

    @ApiOperation("테마추천 코멘트 등록")
    @PostMapping("/comments")
    fun createRecommendedItemComment(@RequestHeader headers: Map<String, String>,
                                     @RequestBody recommendedItemCommentRequest: RecommendedItemCommentRequest): Mono<RecommendedItemCommentResponse> {

        val item = service.createRecommendedItemComment(headers, recommendedItemCommentRequest)

        return Mono.just(item.toRecommendedItemCommentResponse())
    }

    @ApiOperation("테마추천 코멘트 수정")
    @PutMapping("/comments/{id}")
    fun updateRecommendedItemComment(@RequestHeader headers: Map<String, String>,
                                     @PathVariable id: Long,
                                     @RequestBody recommendedItemCommentRequest: RecommendedItemCommentRequest
    ): Mono<RecommendedItemCommentResponse> {

        val item = service.updateRecommendedItemComment(headers, id, recommendedItemCommentRequest)

        return Mono.just(item.toRecommendedItemCommentResponse())
    }

    @ApiOperation("테마추천 코멘트 삭제")
    @DeleteMapping("/comments/{id}")
    fun deleteRecommendedItemComment(@RequestHeader headers: Map<String, String>, @PathVariable id: Long) {

        val uuid : String? = headers["uuid"]
        service.deleteRecommendedItemComment(headers, id)
    }


}