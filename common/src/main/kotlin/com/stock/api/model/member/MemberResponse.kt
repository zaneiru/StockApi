package com.stock.api.model.member

import com.stock.api.entity.member.Member

data class MemberResponse (
    val uuid: String,
    val nickName: String,
    val profileImageUrl: String?
)

fun Member.toMemberResponse(): MemberResponse {
    return MemberResponse (
        uuid = this.uuid!!,
        nickName = this.nickName!!,
        profileImageUrl = this.profileImageUrl
    )
}

