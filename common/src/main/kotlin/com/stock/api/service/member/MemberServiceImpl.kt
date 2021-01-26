package com.stock.api.service.member

import com.stock.api.model.enums.AuthVendorType
import com.stock.api.model.enums.MemberStatus
import com.stock.api.model.member.MemberSignUpRequest
import com.stock.api.entity.member.Member
import com.stock.api.exception.StockException
import com.stock.api.exception.enums.ErrorMessage
import com.stock.api.repository.member.MemberRepository
import com.stock.api.utils.KeyUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.LocalDateTime

@Service
class MemberServiceImpl(private val memberRepository: MemberRepository): MemberService {

    @Transactional(readOnly = true)
    override fun getMember(authVendorType: AuthVendorType, email: String): Mono<Member> {
        email.takeIf { StringUtils.isBlank(it) }?: throw StockException(ErrorMessage.PARAM_EMAIL_EMPTY)

        return Mono.fromCallable {
            memberRepository.findByAuthVendorTypeAndEmail(authVendorType, email)?: throw Exception("")
        }.subscribeOn(Schedulers.parallel())
    }

    @Transactional
    override fun signUp(memberSignUpRequest: MemberSignUpRequest) {
        // 이미 가입된 사용자 인지 체크
        val kakaoMember =
            when (memberSignUpRequest.authVendorType) {
                AuthVendorType.KAKAO -> getKakaoMember(memberSignUpRequest.email, memberSignUpRequest.kakaoNumber)
                else -> throw StockException(ErrorMessage.ETC_INTERNAL_ERROR)
        }

        // 가입된 사용자라면 에러를 내뱉는다.
        if (kakaoMember?.email.equals(memberSignUpRequest.email)) {
            throw StockException(ErrorMessage.MEMBER_SIGN_UP_ALREADY_MEMBER)
        }

        // 위에서 체크가 끝났기 때문에 가입절차를 진행한다.
        val currentDate = LocalDateTime.now()
        val member = Member(
            uuid = KeyUtils.getGenerateKey(),
            nickName = memberSignUpRequest.nickName,
            profileImageUrl = memberSignUpRequest.profileImageUrl,
            status = MemberStatus.NORMAL,
            authVendorType = AuthVendorType.KAKAO,
            accountId = memberSignUpRequest.accountId,
            email = memberSignUpRequest.email,
            gender = memberSignUpRequest.gender,
            age = memberSignUpRequest.age,
            kakaoNumber = memberSignUpRequest.kakaoNumber,
            kakaoNickname = memberSignUpRequest.kakaoNickname!!,
            device = memberSignUpRequest.device,
            lastAppAccessDate = currentDate,
            lastLoginDate = currentDate,
            lastSignupDate = currentDate,
            membershipProduct = null,
            membershipStartDate = null,
            membershipEndDate = null,
            recommendedItemLikes = setOf(),
            recommendedItemComments = setOf()
        )
        val savedMember = memberRepository.save(member)
    }

    @Transactional(readOnly = true)
    override fun getMember(uuid: String): Member? {
        return memberRepository.findByUuid(uuid)?: throw StockException(ErrorMessage.ETC_DOMAIN_VALIDATION_ERROR)
    }

    @Transactional(readOnly = true)
    override fun getKakaoMember(email: String, kakaoNumber: String): Member? {
        if (StringUtils.isEmpty(email)) throw StockException(ErrorMessage.PARAM_EMAIL_EMPTY)
        if (StringUtils.isEmpty(kakaoNumber)) throw StockException(ErrorMessage.PARAM_KAKAO_NUMBER_EMPTY)

        return memberRepository.findByAuthVendorTypeAndEmailAndKakaoNumber(AuthVendorType.KAKAO, email, kakaoNumber)
    }

    @Transactional(readOnly = true)
    override fun getKakaoMember(uuid: String): Mono<Member>? {
        return Mono.fromCallable {
            memberRepository.findByUuid(uuid)?: throw StockException(ErrorMessage.MEMBER_NOT_EXIST)
        }.subscribeOn(Schedulers.parallel())
    }

}