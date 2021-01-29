package com.stock.api.service.item

import com.stock.api.entity.item.Report
import com.stock.api.exception.StockException
import com.stock.api.exception.enums.ErrorMessage
import com.stock.api.model.enums.AdminConfig
import com.stock.api.repository.items.ReportRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportImpl (val reportRepository: ReportRepository): ReportService {

    @Transactional(readOnly = true)
    override fun getReports(pageable: Pageable): Page<Report> {
        return reportRepository.findReports(AdminConfig.ON, pageable)
    }

    @Transactional
    override fun getReport(id: Long): Report {
        val item = reportRepository.findReport(id, AdminConfig.ON)
            ?: throw StockException(ErrorMessage.REPORT_NOT_EXISTS)

        item.apply {
            viewCount += 1
        }
        return reportRepository.save(item)
    }
}