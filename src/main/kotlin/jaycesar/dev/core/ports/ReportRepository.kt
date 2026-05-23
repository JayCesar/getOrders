package jaycesar.dev.core.ports

import jaycesar.dev.core.domain.DeliveryReport
import jaycesar.dev.core.domain.DeliveryReportSummary
import jaycesar.dev.core.domain.ReportTrend
import java.time.LocalDateTime

interface ReportRepository {
    fun findSummaries(limit: Int, offset: Int): List<DeliveryReportSummary>
    fun findTrend(limit: Int): List<ReportTrend>
    fun findById(id: Long): DeliveryReport?
    fun findSummariesByDateRange(from: LocalDateTime, to: LocalDateTime): List<DeliveryReportSummary>
    fun findTrendByDateRange(from: LocalDateTime, to: LocalDateTime): List<ReportTrend>
}
