package com.stock.api.service.member

import com.stock.api.model.enums.AuthVendorType
import com.stock.api.model.member.MemberSignUpRequest
import com.stock.api.entity.member.Member
import reactor.core.publisher.Mono

interface MemberService {
    fun getMember(authVendorType: AuthVendorType, email: String) : Mono<Member>?
    fun signUp(memberSignUpRequest: MemberSignUpRequest)
    fun getKakaoMember(email: String, kakaoNumber: String): Member?
    fun getKakaoMember(uuid: String): Mono<Member>?
    fun getMember(uuid: String): Member?
}