package com.stock.api.service.item

import com.stock.api.entity.item.RecommendedItem
import com.stock.api.entity.item.RecommendedItemComment
import com.stock.api.exception.StockException
import com.stock.api.exception.enums.ErrorMessage
import com.stock.api.model.enums.AdminConfig
import com.stock.api.model.enums.MemberStatus
import com.stock.api.model.enums.UserConfig
import com.stock.api.model.item.RecommendedItemCommentRequest
import com.stock.api.model.item.toRecommendedItemComment
import com.stock.api.repository.items.RecommendedItemCommentRepository
import com.stock.api.repository.items.RecommendedItemRepository
import com.stock.api.service.member.MemberService
import com.stock.api.utils.multiIf
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecommendedItemImpl(
    private val memberService: MemberService,
    private val repository: RecommendedItemRepository,
    private val commentRepository: RecommendedItemCommentRepository
) : RecommendedItemService {

    companion object {
        const val HEADER_UUID: String = "uuid"
        const val HEADER_IP: String = "ip"
    }

    @Transactional(readOnly = true)
    override fun getRecommendedItems(pageable: Pageable): Page<RecommendedItem> {
        return repository.findByAdminConfigOrderByLastModifiedDateDesc(AdminConfig.ON, pageable)
    }

    @Transactional(readOnly = true)
    override fun getRecommendedItem(id: Long): RecommendedItem {
        return repository.findByIdAndAdminConfig(id, AdminConfig.ON)
            ?: throw StockException(ErrorMessage.RECOMMENDED_ITEM_NOT_EXIST)
    }

    @Transactional
    override fun createRecommendedItemComment(headers: Map<String, String>, request: RecommendedItemCommentRequest): RecommendedItemComment {

        val uuid = headers[HEADER_UUID]
        val ip = headers[HEADER_IP]

        multiIf(
            (uuid == null) to { throw StockException(ErrorMessage.MEMBER_UUID_IS_EMPTY) },
            (request.comment == null) to { throw StockException(ErrorMessage.ETC_DOMAIN_VALIDATION_ERROR) },
            // 코멘트 길이 validation 추가
        )

        val recommendedItem = getRecommendedItem(request.recommendedItemId)
        val member = memberService.getMember(uuid!!)

        return commentRepository.save(request.toRecommendedItemComment(ip!!, recommendedItem, member!!))
    }

    @Transactional
    override fun updateRecommendedItemComment(headers: Map<String, String>, id: Long, request: RecommendedItemCommentRequest): RecommendedItemComment {

        val uuid = headers[HEADER_UUID]
        val ip = headers[HEADER_IP]

        multiIf(
            (uuid == null) to { throw StockException(ErrorMessage.MEMBER_UUID_IS_EMPTY) },
            (id != request.id) to { throw StockException(ErrorMessage.COMMENT_ID_NOT_VALID) },
            (request.comment == null) to { throw StockException(ErrorMessage.ETC_DOMAIN_VALIDATION_ERROR) },
            // 코멘트 길이 validation 추가
        )

        val createdComment= commentRepository.findByIdAndUserConfigAndAdminConfig(id, UserConfig.ON, AdminConfig.ON)
            ?: throw StockException(ErrorMessage.COMMENT_NOT_EXIST)

        val updateComment = createdComment.apply {
            this.ip = ip
            comment = request.comment
            recommendedItem?.commentCount = recommendedItem?.commentCount!! + 1
        }

        return commentRepository.save(updateComment)
    }


    @Transactional
    override fun deleteRecommendedItemComment(headers: Map<String, String>, id: Long) {

        val uuid = headers[HEADER_UUID]
        val ip = headers[HEADER_IP]

        multiIf(
            (uuid == null) to { throw StockException(ErrorMessage.MEMBER_UUID_IS_EMPTY) },
        )

        val createdComment = commentRepository.findByIdAndUserConfigAndAdminConfig(id, UserConfig.ON, AdminConfig.ON)
            ?: throw StockException(ErrorMessage.COMMENT_NOT_EXIST)

        multiIf(
            (createdComment.member!!.uuid != uuid) to { throw StockException(ErrorMessage.COMMENT_NOT_OWNER) },
            (createdComment.member!!.status != MemberStatus.NORMAL) to { throw StockException(ErrorMessage.RECOMMENDED_ITEM_NOT_EXIST) }
        )

        val deleteComment = createdComment.apply {
            this.ip = ip
            userConfig = UserConfig.DEL
            recommendedItem?.commentCount = recommendedItem?.commentCount!! - 1
        }

        commentRepository.save(deleteComment)
    }
}

