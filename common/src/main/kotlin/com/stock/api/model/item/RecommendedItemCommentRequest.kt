package com.stock.api.model.item

import com.stock.api.entity.NoArg
import com.stock.api.entity.item.RecommendedItem
import com.stock.api.entity.item.RecommendedItemComment
import com.stock.api.entity.member.Member
import com.stock.api.model.enums.AdminConfig
import com.stock.api.model.enums.UserConfig

@NoArg
class RecommendedItemCommentRequest(
    val id: Long? = null,
    val comment: String?,
    val ip: String,
    val userConfig: String?,
    val recommendedItemId: Long
)

fun RecommendedItemCommentRequest.toRecommendedItemComment(
    ip: String,
    recommendedItem: RecommendedItem,
    member: Member
): RecommendedItemComment {
    return RecommendedItemComment(
        recommendedItem = recommendedItem,
        member = member,
        ip = ip,
        comment = this.comment,
        userConfig = UserConfig.ON,
        adminConfig = AdminConfig.ON
    )
}
