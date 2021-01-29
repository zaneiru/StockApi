package com.stock.api.model.item

import com.stock.api.entity.item.RecommendedItem
import com.stock.api.entity.item.RecommendedItemComment
import java.time.LocalDateTime

data class RecommendedItemResponse (

    var id: Long,
    var title: String,
    var bannerUrl: String,
    var bannerMainTitle: String,
    var bannerSubTitle: String,
    var likeCount: Int,
    var viewCount: Int,
    var commentCount: Int,
    var lastModifiedDate: LocalDateTime
)

fun RecommendedItem.toRecommendedItemResponse(): RecommendedItemResponse {
    return RecommendedItemResponse(
        id = this.id!!,
        title = this.title!!,
        bannerUrl = this.bannerUrl!!,
        bannerMainTitle = this.bannerMainTitle!!,
        bannerSubTitle = this.bannerSubTitle!!,
        likeCount = this.likeCount,
        viewCount = this.viewCount,
        commentCount = this.commentCount,
        lastModifiedDate = this.lastModifiedDate
    )
}
