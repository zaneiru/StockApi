package com.stock.api.service.item

import com.stock.api.entity.item.Report
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReportService {

    fun getReports(pageable: Pageable): Page<Report>
    fun getReport(id: Long): Report?
}