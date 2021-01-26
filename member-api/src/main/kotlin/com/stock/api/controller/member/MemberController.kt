package com.stock.api.controller.member

import com.stock.api.controller.member.MemberController.Companion.BASE_URI
import com.stock.api.controller.member.to.MemberResponseTO
import com.stock.api.controller.member.to.toMemberResponseTO
import com.stock.api.model.enums.AuthVendorType
import com.stock.api.model.member.MemberSignUpRequest
import com.stock.api.entity.member.Member
import com.stock.api.service.member.MemberService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@Api(description = "멤버 API")
@RestController
@RequestMapping(value = [BASE_URI])
class MemberController(val memberService: MemberService) {

    @ApiOperation("멤버 조회 by 스톡스프레이 uuid")
    @GetMapping("/{uuid}")
    fun getMember(@PathVariable uuid: String): Mono<MemberResponseTO>? {
        val kakaoMember = memberService.getKakaoMember(uuid)
        return kakaoMember?.map(Member::toMemberResponseTO)
    }

    @ApiOperation("멤버 조회")
    @GetMapping
    fun getMember(
        @RequestParam("authVendorType") authVendorType: AuthVendorType,
        @RequestParam("email") email: String
    ) {
        val member = memberService.getMember(authVendorType, email)
    }

    @ApiOperation("멤버 가입")
    @PostMapping("/signUp")
    fun signUp(@RequestBody memberSignUpRequest: MemberSignUpRequest) {
        memberService.signUp(memberSignUpRequest)
//        val apiError = ApiError(
//            errorCode = 100100,
//            message = "하.......",
//            status = HttpStatus.BAD_REQUEST
//        )
//        return ResponseEntity.badRequest()
//            .header(HttpHeaders.CONTENT_TYPE, "application/json")
//            .body(apiError)
    }

    companion object {
        const val BASE_URI: String = "/api/v1/members"
    }
}

