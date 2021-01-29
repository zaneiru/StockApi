package com.stock.api.model.item

import com.fasterxml.jackson.annotation.JsonProperty
import com.stock.api.entity.item.RecommendedItemComment
import com.stock.api.model.member.MemberResponse
import com.stock.api.model.member.toMemberResponse

data class RecommendedItemCommentResponse (
    val id: Long?,
    val comment: String?,
    val userConfig: String,
    @JsonProperty("member")
    val memberResponse: MemberResponse,
    val lastModifiedDate: String
)

fun RecommendedItemComment.toRecommendedItemCommentResponse(): RecommendedItemCommentResponse {
    return RecommendedItemCommentResponse(
        id = this.id,
        comment = this.comment,
        userConfig = this.userConfig.toString(),
        memberResponse = this.member!!.toMemberResponse(),
        lastModifiedDate = this.lastModifiedDate.toString()
    )
}

