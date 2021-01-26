package com.stock.api.model.member

import com.stock.api.entity.member.Member

data class MemberRequest (
    val uuid: String?
)

fun MemberRequest.toMember(): Member {
    return Member(
        uuid = this.uuid
    )
}