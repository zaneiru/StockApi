package com.stock.api.controller.report

import com.stock.api.entity.item.Report
import com.stock.api.model.item.ReportResponse
import com.stock.api.model.item.toReportResponse
import com.stock.api.service.item.ReportService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@Api(description = "증권사 레포트 API")
@RestController
@RequestMapping(value = [ReportController.BASE_URI])
class ReportController(val service: ReportService) {

    companion object {
        const val BASE_URI: String = "/api/v1/reports"
    }

    @ApiOperation("증권사 레포트 목록 조회")
    @GetMapping
    fun getReport(pageable: Pageable): Mono<Page<ReportResponse>> {
        val items = service.getReports(pageable)
        return Mono.just(items.map(Report::toReportResponse))
    }

    @ApiOperation("증권사 레포트 조회")
    @GetMapping("/{id}")
    fun getReport(@PathVariable id: Long): Mono<ReportResponse>? {
        val item = service.getReport(id)
        return Mono.just(item!!.toReportResponse())
    }

}