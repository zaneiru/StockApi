package com.stock.api.service.item

import com.stock.api.entity.item.RecommendedItem
import com.stock.api.entity.item.RecommendedItemComment
import com.stock.api.model.item.RecommendedItemCommentRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface RecommendedItemService {

    fun getRecommendedItems(pageable: Pageable): Page<RecommendedItem>

    fun getRecommendedItem(id: Long): RecommendedItem

    fun createRecommendedItemComment(headers: Map<String, String>, request: RecommendedItemCommentRequest): RecommendedItemComment

    fun updateRecommendedItemComment(headers: Map<String, String>, id: Long, request: RecommendedItemCommentRequest): RecommendedItemComment

    fun deleteRecommendedItemComment(headers: Map<String, String>, id: Long)
}