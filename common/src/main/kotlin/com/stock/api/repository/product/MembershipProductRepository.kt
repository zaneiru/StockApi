package com.stock.api.repository.product

import com.stock.api.model.enums.AdminConfig
import com.stock.api.model.enums.MembershipProductCode
import com.stock.api.model.enums.YesNoConfig
import com.stock.api.entity.product.MembershipProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MembershipProductRepository : JpaRepository<MembershipProduct, Long> {

    fun findByAdminConfigAndShowConfig(adminConfig: AdminConfig, showConfig: YesNoConfig) : List<MembershipProduct>

    fun findByCode(code: MembershipProductCode): MembershipProduct?
}