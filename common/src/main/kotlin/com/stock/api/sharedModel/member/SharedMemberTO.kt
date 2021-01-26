package com.stock.api.sharedModel.member

import com.stock.api.entity.NoArg
import com.stock.api.entity.member.Member

@NoArg
data class SharedMemberTO (
    val uuid: String,
    val nickName: String,
    val profileImageUrl: String?
)
//
//fun Member.toSharedMemberTO(): SharedMemberTO {
//    return SharedMemberTO (
////        uuid = this.uuid,
////        nickName = this.nickName,
//        profileImageUrl = this.profileImageUrl
//    )
//}