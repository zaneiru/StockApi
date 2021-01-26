package com.stock.api.entity.news

import com.fasterxml.jackson.annotation.JsonFormat
import com.stock.api.model.enums.FdaApprovalType
import com.stock.api.model.enums.NewsType
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class News (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    var type: NewsType,

    @Column(nullable = true)
    var press: String?,

    @Column(nullable = true)
    var title: String?,

    @Column(nullable = true)
    var link: String?,

    @Column(nullable = true)
    var fdaRequester: String?,

    @Column(nullable = true)
    var fdaProductName: String?,

    @Column(nullable = true)
    var fdaStep: Int?,

    @Column(nullable = true)
    var fdaInstitution: String?,

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    var fdaApprovalType: FdaApprovalType?,

    @Column(nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var fdaApprovalDate: LocalDateTime?,

    @Column(nullable = true, columnDefinition = "TEXT")
    var fdaClinicalContent: String?,

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val scrapeDate: LocalDateTime

) {
    companion object {
        private const val serialVersionUID = -7917223340774147552L;
    }
}
