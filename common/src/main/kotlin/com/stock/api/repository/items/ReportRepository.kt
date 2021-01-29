package com.stock.api.repository.items

import com.stock.api.entity.item.Report
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository : JpaRepository<Report, Long>, CustomizeReportRepository