package com.stock.api.repository.items

import com.stock.api.model.enums.AdminConfig
import com.stock.api.entity.item.RecommendedItem
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecommendedItemRepository : JpaRepository<RecommendedItem, Long> {

    fun findByAdminConfigOrderByLastModifiedDateDesc(adminConfig: AdminConfig, pageable: Pageable) : Page<RecommendedItem>

    fun findByIdAndAdminConfig(id: Long, adminConfig: AdminConfig): RecommendedItem?
}