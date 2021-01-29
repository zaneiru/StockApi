package com.stock.api.repository.items

import com.stock.api.entity.item.Report
import com.stock.api.model.enums.AdminConfig
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomizeReportRepository {

    fun findReports(adminConfig: AdminConfig, pageable: Pageable): Page<Report>
    fun findReport(id: Long, adminConfig: AdminConfig): Report?
}