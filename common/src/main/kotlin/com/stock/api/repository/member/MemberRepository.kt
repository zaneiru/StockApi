package com.stock.api.repository.member

import com.stock.api.model.enums.AuthVendorType
import com.stock.api.entity.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository: JpaRepository<Member, Long> {
    fun findByAuthVendorTypeAndEmail(authVendorType: AuthVendorType, email: String): Member?
    fun findByAuthVendorTypeAndEmailAndKakaoNumber(authVendorType: AuthVendorType, email: String, kakaoNumber: String): Member?
    fun findByUuid(uuid: String): Member?
}