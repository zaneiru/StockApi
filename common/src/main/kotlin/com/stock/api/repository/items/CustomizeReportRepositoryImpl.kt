package com.stock.api.repository.items

import com.querydsl.jpa.JPQLQuery
import com.stock.api.entity.item.QReport
import com.stock.api.entity.item.QReportTag
import com.stock.api.entity.item.Report
import com.stock.api.model.enums.AdminConfig
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class CustomizeReportRepositoryImpl : QuerydslRepositorySupport(Report::class.java), CustomizeReportRepository {

    override fun findReports(adminConfig: AdminConfig, pageable: Pageable): PageImpl<Report> {
        val report = QReport.report
        val reportTag = QReportTag.reportTag
        var query: JPQLQuery<Report> = from(report)
            .leftJoin(report.reportTags, reportTag).fetchJoin()
            .where(report.adminConfig.eq(adminConfig))

        query = querydsl!!.applyPagination(pageable, query)

        return PageImpl(query.fetch(), pageable, query.fetchCount());
    }

    override fun findReport(id: Long, adminConfig: AdminConfig): Report? {
        val report = QReport.report
        val reportTag = QReportTag.reportTag
        val query: JPQLQuery<Report> = from(report)
            .leftJoin(report.reportTags, reportTag).fetchJoin()
            .where(report.id.eq(id).and(report.adminConfig.eq(adminConfig)))

        return query.fetchOne()
    }
}