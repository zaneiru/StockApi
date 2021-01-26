package com.stock.api.controller.product

import com.stock.api.controller.product.MembershipProductController.Companion.BASE_URI
import com.stock.api.controller.product.to.MembershipProductResponseTO
import com.stock.api.controller.product.to.toMembershipProductResponseTO
import com.stock.api.entity.product.MembershipProduct
import com.stock.api.service.prouct.MembershipProductService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Api(description = "멤버 API")
@RestController
@RequestMapping(value = [BASE_URI])
class MembershipProductController(private val membershipProductService: MembershipProductService) {

    @ApiOperation("멤버쉽 상품 목록 조회")
    @GetMapping
    fun getMembershipProducts(): Flux<MembershipProductResponseTO> {
        val membershipProducts = membershipProductService.getMembershipProducts()
        return membershipProducts.map(MembershipProduct::toMembershipProductResponseTO)
    }

    @ApiOperation("멤버쉽 상품 조회")
    @GetMapping("/{code}")
    fun getMembershipProduct(@PathVariable code: String): Mono<MembershipProductResponseTO> {
        val membershipProduct = membershipProductService.getMembershipProduct(code)
        return membershipProduct.map(MembershipProduct::toMembershipProductResponseTO)
    }

    companion object {
        const val BASE_URI: String = "/api/v1/membershipProducts"
    }
}