package jaycesar.dev.core.ports

import jaycesar.dev.core.domain.DeliveryReport
import jaycesar.dev.core.domain.DeliveryReportSummary
import jaycesar.dev.core.domain.ReportTrend

interface ReportService {
    fun listReports(limit: Int, offset: Int): List<DeliveryReportSummary>
    fun getTrend(limit: Int): List<ReportTrend>
    fun getReport(id: Long): DeliveryReport
    fun getRegions(id: Long): Any?
    fun getCarriers(id: Long): Any?
    fun getReasons(id: Long): Any?
}
