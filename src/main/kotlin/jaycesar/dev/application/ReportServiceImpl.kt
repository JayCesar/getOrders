package jaycesar.dev.application

import jaycesar.dev.core.domain.DeliveryReport
import jaycesar.dev.core.domain.DeliveryReportSummary
import jaycesar.dev.core.domain.ReportTrend
import jaycesar.dev.core.ports.ReportRepository
import jaycesar.dev.core.ports.ReportService
import org.springframework.stereotype.Service

@Service
class ReportServiceImpl(private val repository: ReportRepository) : ReportService {

    override fun listReports(limit: Int, offset: Int): List<DeliveryReportSummary> =
        repository.findSummaries(limit, offset)

    override fun getTrend(limit: Int): List<ReportTrend> =
        repository.findTrend(limit)

    override fun getReport(id: Long): DeliveryReport =
        repository.findById(id) ?: throw NoSuchElementException("Report $id not found")

    override fun getRegions(id: Long): Any? = getReport(id).criticalRegions

    override fun getCarriers(id: Long): Any? = getReport(id).criticalCarriers

    override fun getReasons(id: Long): Any? = getReport(id).topFailureReasons
}
