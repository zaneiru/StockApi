package com.stock.api.service.prouct

import com.stock.api.model.enums.AdminConfig
import com.stock.api.model.enums.MembershipProductCode
import com.stock.api.model.enums.YesNoConfig
import com.stock.api.entity.product.MembershipProduct
import com.stock.api.repository.product.MembershipProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class MembershipProductService(private val membershipProductRepository: MembershipProductRepository) {

    @Transactional(readOnly = true)
    fun getMembershipProducts(): Flux<MembershipProduct> =
        Mono.fromCallable { membershipProductRepository.findByAdminConfigAndShowConfig(AdminConfig.ON, YesNoConfig.Y) }
            .subscribeOn(Schedulers.parallel())
            .flatMapMany { Flux.fromIterable(it) }

    @Transactional(readOnly = true)
    fun getMembershipProduct(code: String): Mono<MembershipProduct> =
        Mono.fromCallable { membershipProductRepository.findByCode(MembershipProductCode.valueOf(code))?: throw Exception("") }
            .subscribeOn(Schedulers.parallel())

    @Transactional
    fun paymentMembership() {

    }
}