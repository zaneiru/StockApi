package com.stock.api.repository.items

import com.stock.api.entity.item.RecommendedItemComment
import com.stock.api.model.enums.AdminConfig
import com.stock.api.model.enums.UserConfig
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecommendedItemCommentRepository : JpaRepository<RecommendedItemComment, Long> {

    fun findByIdAndUserConfigAndAdminConfig(id: Long, userConfig: UserConfig, adminConfig: AdminConfig):
            RecommendedItemComment?

    fun findByRecommendedItemIdAndUserConfigAndAdminConfigOrderByLastModifiedDateDesc(
        recommendedItemId: Long,
        userConfig: UserConfig,
        adminConfig: AdminConfig,
        pageable: Pageable
    ): Page<RecommendedItemComment>
}